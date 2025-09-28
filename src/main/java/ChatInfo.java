import java.io.File;
import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ChatInfo {

    private static final List<String> BAD_ENDINGS = List.of(
            "Video weggelassen",
            "Bild weggelassen",
            "Sticker weggelassen",
            "Audio weggelassen",
            "Diese Nachricht wurde gelöscht",
            " hinzugefügt.",
            "diese Gruppe erstellt."
    );
    static private Message lastMessage;
    private List<Message> messageListGes;
    private Set<String> chatterSet;
    private String name;
    private File file;

    public ChatInfo (File file) throws FileNotFoundException {
        this.file = file;

        this.name = file.getName();
        if (this.name.endsWith("_chat.txt")) {
            this.name = this.name.replace("_chat.txt", "");
        }
        else {
            this.name = this.name.replace(".txt", "");
        }
        this.name = this.name.replace("_", " ");

        messageListGes = new LinkedList<>();
        chatterSet = new HashSet<>();

        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String message = reader.nextLine();
            saveMessage(message);
        }
        reader.close();
    }

    private void saveMessage(String message) {
        Message messageStruct;
        message = message.trim();
        if (message.matches("\\[\\d{2}.\\d{2}.\\d{2}, \\d{2}:\\d{2}:\\d{2}].*")) {

            String[] info = message.split(" ", 4);
            while (!info[2].endsWith(":")) {
                String[] helper = info[3].split(" ", 2);
                info[2] = info[2] + " " + helper[0];
                info[3] = helper[1];
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'['dd.MM.yy,HH:mm:ss']'");
            LocalDateTime dateTime = LocalDateTime.parse(info[0] + info[1], formatter);

            String sender = info[2].replace(":", "");
            String text = "";
            if (info.length > 3) {
                text = info[3];
            }

            messageStruct = new Message(text, sender, dateTime);

            lastMessage = messageStruct;

            if (isValidMessage(text)) {
                messageListGes.add(messageStruct);
                chatterSet.add(sender);
            }
        } else {
            lastMessage.appendMessage(" " + message);
        }
    }

    private boolean isValidMessage(String text) {
        return BAD_ENDINGS.stream().noneMatch(text::endsWith)
                && !text.contains("Du hast die Gruppe ")
                && !text.contains("Nachrichten und Anrufe sind Ende-zu-Ende-verschlüsselt.");
    }

    private List<Message> getMessagesByChatter(String name, List<Message> messageList) {
        List<Message> listByChatter = new LinkedList<>();
        if (!chatterSet.contains(name)) {
            return listByChatter;
        }

        for (Message message : messageList) {
            if (message.getSender().equals(name)) {
                listByChatter.add(message);
            }
        }
        return listByChatter;
    }

    public Map<String, Integer> callMessages(MessageListFilter filter) throws NumberFormatException {
        List<Message> filteredList = filter.filterList(messageListGes);

        Map<String, Integer> results = new HashMap<>();

        int gesMessageNum = filteredList.size();

        results.put("Total", gesMessageNum);

        for (String sender : chatterSet) {
            int senderMessageNum = getMessagesByChatter(sender, filteredList).size();
            results.put(sender, senderMessageNum);
        }
        return results;
    }

    public Map<String, Integer> callWords(MessageListFilter filter) throws NumberFormatException {
        List<Message> filteredList = filter.filterList(messageListGes);

        Map<String, Integer> results = new HashMap<>();

        int wordsGes = 0;
        for (Message message : filteredList) {
            wordsGes += message.getWords();
        }
        results.put("Total", wordsGes);

        for (String sender : chatterSet) {
            int wordsChatter = 0;
            for (Message message : getMessagesByChatter(sender, filteredList)) {
                wordsChatter += message.getWords();
            }
            results.put(sender, wordsChatter);
        }
        return results;
    }

    public Map<String, Float> callWordsPerMessage(MessageListFilter filter) throws NumberFormatException {
        List<Message> filteredList = filter.filterList(messageListGes);

        Map<String, Float> results = new HashMap<>();

        int wordsGes = 0;
        for (Message message : filteredList) {
            wordsGes += message.getWords();
        }

        if (!filteredList.isEmpty()) {
            results.put("Total", ((float)(wordsGes) / filteredList.size()));
        }
        else {
            results.put("Total", (float)0);
        }

        for (String sender : chatterSet) {
            int wordsChatter = 0;
            for (Message message : getMessagesByChatter(sender, filteredList)) {
                wordsChatter += message.getWords();
            }
            if (!getMessagesByChatter(sender, filteredList).isEmpty()) {
                results.put(sender, ((float)(wordsChatter) / getMessagesByChatter(sender, filteredList).size()));
            }
            else {
                results.put(sender, (float)0);
            }
        }
        return results;
    }

    public Map<String, Integer> callDigga(MessageListFilter filter) throws NumberFormatException {
        List<Message> filteredList = filter.filterList(messageListGes);

        Map<String, Integer> results = new HashMap<>();

        int diggaGes = 0;
        for (Message message : filteredList) {
            diggaGes += message.getDigga();
        }
        results.put("Total", diggaGes);

        for (String sender : chatterSet) {
            int diggaChatter = 0;
            for (Message message : getMessagesByChatter(sender, filteredList)) {
                diggaChatter += message.getDigga();
            }
            results.put(sender, diggaChatter);
        }
        return results;
    }

    public Map<String, LocalTime> callAnswerTime(int maxHours) {
        Map<String, LocalTime> result = new HashMap<>();

        for (String chatter : chatterSet) {
            LocalTime duration;
            
            duration = LocalTime.MIN.plusSeconds(getAvgAnswerTimeByChatter(chatter, maxHours));
            result.put(chatter, duration);
        }
        return result;
    }

    public int getAvgAnswerTimeByChatter(String chatter, int maxHours) {
        long answerTimeSecondsGes = 0;
        int answers = 0;

        LocalDateTime lastFromOther = null;
        for (Message message : messageListGes) {
            if (message.getSender().equals(chatter)) {
                if (lastFromOther != null && Duration.between(lastFromOther, message.getDateTime()).toHours() <= maxHours) {
                    answers++;
                    answerTimeSecondsGes += Duration.between(lastFromOther, message.getDateTime()).toSeconds();
                }
                lastFromOther = null;
            } 
            else {
                lastFromOther = message.getDateTime();
            }
        }
        if (answers == 0) {
            return 0;
        }
        return (int) answerTimeSecondsGes/answers;
    }

    public Map<String, Integer> callAnswers(boolean filter, int fromMin, int toMin) throws NumberFormatException{
        Map<String, Integer> results = new HashMap<>();
        if (!filter) {
            for (String chatter : chatterSet) {
                results.put(chatter, answersGes(chatter));
            }
        }
        else {
            for (String chatter : chatterSet) {
                int answersBetween = answersBetween(chatter, fromMin, toMin);
                results.put(chatter, answersBetween);
            } 
        }
        return results;
    }

    public int answersBetween(String chatter, int fromMin, int toMin) {
        int answers = 0;

        LocalDateTime lastFromOther = null;
        for (Message message : messageListGes) {
            if (message.getSender().equals(chatter)) {
                if (lastFromOther != null) {
                    long durationMins = Duration.between(lastFromOther, message.getDateTime()).toMinutes();
                    if (durationMins >= fromMin && durationMins <= toMin) {
                        answers++;
                    }
                    lastFromOther = null;
                }
            } 
            else {
                lastFromOther = message.getDateTime();
            }
        }
        return answers;
    }

    public int answersGes(String chatter) {
        int answers = 0;

        LocalDateTime lastFromOther = null;
        for (Message message : messageListGes) {
            if (message.getSender().equals(chatter)) {
                if (lastFromOther != null) {
                    answers++;
                    lastFromOther = null;
                }
            } 
            else {
                lastFromOther = message.getDateTime();
            }
        }
        return answers;
    }

    public Set<String> getChatterSet() {
        return chatterSet;
    }

    public List<Message> getMessageListGes() {
        return messageListGes;
    }

    public String getName() {
        return name;
    }
}
