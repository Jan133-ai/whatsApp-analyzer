import java.io.File;
import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ChatInfo {

    static private Message lastMessage;
    
    private List<Message> messageListGes;
    private List<String> chatterList;
    private String fileName;
    private String name;

    public ChatInfo (String fileName) throws FileNotFoundException {

        this.fileName = fileName;

        this.name = fileName;

        if (this.name.startsWith("Chats/")) {
            this.name = this.name.replace("Chats/", "");
        }

        if (this.name.endsWith("_chat.txt")) {
            this.name = this.name.replace("_chat.txt", "");
        }
        else {
            this.name = this.name.replace(".txt", "");
        }


        messageListGes = new LinkedList<>();
        chatterList = new LinkedList<>();
        File chatText = new File(fileName);
        Scanner reader = new Scanner(chatText);
        while (reader.hasNextLine()) {
            String message = reader.nextLine();
            saveMessage(message);
        }
        reader.close();
    }

    private void saveMessage(String message) {

        Message messageStruct;
        message = message.trim();
        if (message.matches("\\[\\d{2}.\\d{2}.\\d{2}, \\d{2}:\\d{2}:\\d{2}\\].*")) {

            String[] info = message.split(" ", 4);
            while (!info[2].endsWith(":")) {

                String[] helper = info[3].split(" ", 2);
                info[2] = info[2] + "_" + helper[0];
                if (info.length > 3) {
                    info[3] = helper[1];
                }
            }

            String[] date = info[0].split("[\\[.,]");

            int day = Integer.parseInt(date[1]);
            int month = Integer.parseInt(date[2]);
            int year = Integer.parseInt(date[3]) + 2000;

            String[] time = info[1].split("[\\]:]");

            int hour = Integer.parseInt(time[0]);
            int minute = Integer.parseInt(time[1]);
            int second = Integer.parseInt(time[2]);


            LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute, second);
            String sender = info[2].replace(":", "");
            String text = "";
            if (info.length > 3) {
                text = info[3];
            }

            messageStruct = new Message(text, sender, dateTime);

            lastMessage = messageStruct;

            if (!chatterList.contains(sender)) {
                chatterList.add(sender);
            }

            if (!messageStruct.getText().endsWith("Video weggelassen")
                && !messageStruct.getText().endsWith("Bild weggelassen")
                && !messageStruct.getText().endsWith("Sticker weggelassen")
                && !messageStruct.getText().endsWith("Diese Nachricht wurde gelöscht")
                && !messageStruct.getText().endsWith("Nachrichten und Anrufe sind Ende-zu-Ende-verschlüsselt. Niemand außerhalb dieses Chats kann sie lesen oder anhören, nicht einmal WhatsApp.")
                && !messageStruct.getText().contains("Du hast die Gruppe ")
                && !messageStruct.getText().endsWith("Nachrichten und Anrufe sind Ende-zu-Ende-verschlüsselt. Niemand außerhalb dieses Chats kann sie lesen oder anhören, nicht einmal WhatsApp.")) {
                    messageListGes.add(messageStruct);
                }
        } else {
            lastMessage.appendMessage(" " + message);
        }
    }

    private List<Message> getMessagesByChatter(String name, List<Message> messageList) {
        
        List<Message> listByChatter = new LinkedList<>();
        if (!chatterList.contains(name)) {
            return listByChatter;
        }

        for (Message message : messageList) {
            if (message.getSender().equals(name)) {
                listByChatter.add(message);
            }
        }
        return listByChatter;
    }

    // if day = 0 -> all days in month; if day = 0 and moth = 0 -> all days in year
    private List<Message> getMessagesByDate(String dateString, List<Message> messageList) throws NumberFormatException{ 
        List<Message> listByDate = new LinkedList<>();

        int year = 0;
        int month = 0;
        int day = 0;

        String[] date = dateString.split("[.]");
        if (date.length == 3) {

            day = Integer.parseInt(date[0]);
            month = Integer.parseInt(date[1]);
            year = Integer.parseInt(date[2]);
        }
        else if (date.length == 2) {
            month = Integer.parseInt(date[0]);
            year = Integer.parseInt(date[1]);
        }
        else if (date.length == 1) {
            year = Integer.parseInt(date[0]);
        }

        for (Message message : messageList) {
            if ((message.getDateTime().getYear() == year && message.getDateTime().getMonth().getValue() == month 
            && message.getDateTime().getDayOfMonth() == day)
            || (message.getDateTime().getYear() == year && message.getDateTime().getMonth().getValue() == month && day == 0)
            || (message.getDateTime().getYear() == year && month == 0 && day == 0)) {
                listByDate.add(message);
            }
        }
        return listByDate;
    }

    private List<Message> filterListByFlags(String flags, String value) throws NumberFormatException {
        if (!flags.startsWith("-") || value.equals("")) {
            return messageListGes;
        }

        if (flags.contains("d")) {
            return getMessagesByDate(value, messageListGes);
        }
        return messageListGes;
    }    

    public Map<String, Integer> callMessages(boolean filter, String flags, String value) throws NumberFormatException {

        List<Message> filteredList = messageListGes;

        Map<String, Integer> results = new HashMap<String, Integer>();

        if (filter) {
            filteredList = filterListByFlags(flags, value);
        }

        int gesMessageNum = filteredList.size();

        results.put("Total", gesMessageNum);

        for (String sender : chatterList) {
            int senderMessageNum = getMessagesByChatter(sender, filteredList).size();
            results.put(sender, senderMessageNum);
        }

        return results;
    }

    public Map<String, Integer> callWords(boolean filter, String flags, String value) throws NumberFormatException {

        List<Message> filteredList = messageListGes;

        Map<String, Integer> results = new HashMap<String, Integer>();

        if (filter) {
            filteredList = filterListByFlags(flags, value);
        }

        int wordsGes = 0;
        for (Message message : filteredList) {
            wordsGes += message.getWords();
        }
        results.put("Total", wordsGes);

        for (String sender : chatterList) {
            int wordsChatter = 0;
            for (Message message : getMessagesByChatter(sender, filteredList)) {
                wordsChatter += message.getWords();
            }
            results.put(sender, wordsChatter);
        }

        return results;
    }

    public Map<String, Float> callWordsPerMessage(boolean filter, String flags, String value) throws NumberFormatException {

        List<Message> filteredList = messageListGes;

        Map<String, Float> results = new HashMap<String, Float>();

        if (filter) {
            filteredList = filterListByFlags(flags, value);
        }

        int wordsGes = 0;
        for (Message message : filteredList) {
            wordsGes += message.getWords();
        }

        if (filteredList.size() != 0) {
            results.put("Total", ((float)(wordsGes) / filteredList.size()));
        }
        else {
            results.put("Total", (float)0);
        }

        for (String sender : chatterList) {
            int wordsChatter = 0;
            for (Message message : getMessagesByChatter(sender, filteredList)) {
                wordsChatter += message.getWords();
            }
            if (getMessagesByChatter(sender, filteredList).size() != 0) {
                results.put(sender, ((float)(wordsChatter) / getMessagesByChatter(sender, filteredList).size()));
            }
            else {
                results.put(sender, (float)0);
            }
        }

        return results;
    }

    public Map<String, Integer> callDigga(boolean filter, String flags, String value) throws NumberFormatException {

        List<Message> filteredList = messageListGes;

        Map<String, Integer> results = new HashMap<String, Integer>();

        if (filter) {
            filteredList = filterListByFlags(flags, value);
        }

        int diggaGes = 0;
        for (Message message : filteredList) {
            diggaGes += message.getDigga();
        }
        results.put("Total", diggaGes);

        for (String sender : chatterList) {
            int diggaChatter = 0;
            for (Message message : getMessagesByChatter(sender, filteredList)) {
                diggaChatter += message.getDigga();
            }
            results.put(sender, diggaChatter);
        }

        return results;
    }

    public Map<String, LocalTime> callAnswerTime(int maxHours) {
        Map<String, LocalTime> result = new HashMap<String, LocalTime>();

        for (String chatter : chatterList) {
            LocalTime duration = LocalTime.MIN;
            
            duration = LocalTime.MIN.plusSeconds(getAvgAnswerTimeByChatter(chatter, maxHours));
            result.put(chatter, duration);
        }

        return result;
    }

    public int getAvgAnswerTimeByChatter(String chatter, int maxHours) {
        int answerTimeSecondsGes = 0;
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

        return answerTimeSecondsGes/answers;
    }

    public Map<String, Integer> callAnswers(boolean filter, int fromMin, int toMin) throws NumberFormatException{
        Map<String, Integer> results = new HashMap<String, Integer>();
        if (!filter) {
            for (String chatter : chatterList) {
                results.put(chatter, answersGes(chatter));
            }
        }
        else {
            for (String chatter : chatterList) {
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

    public List<String> getChatterList() {
        return chatterList;
    }

    public List<Message> getMessageListGes() {
        return messageListGes;
    }

    public String getFileName() {
        return fileName;
    }

    public String getName() {
        return name;
    }
}
