package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ChatParser {

    Message lastMessage;
    private List<Message> messageListGes;
    private Set<String> chatterSet;
    private File file;

    public ChatParser() {
        messageListGes = new LinkedList<>();
        chatterSet = new HashSet<>();
    }

    public ChatParser(File file) {
        messageListGes = new LinkedList<>();
        chatterSet = new HashSet<>();
        this.file = file;
    }

    public void parseFile() throws FileNotFoundException {
        Scanner reader = new Scanner(this.file);
        while (reader.hasNextLine()) {
            String message = reader.nextLine();
            parseLine(message);
        }
        reader.close();
    }

    public Message parseLine(String messageLine) {
        Message messageStruct;
        messageLine = messageLine.trim();

        boolean mediaInMessage = false;
        if (messageLine.startsWith("\u200e")) {
            mediaInMessage = true;
            messageLine = messageLine.replaceFirst("\u200e", "");
        }

        if (messageLine.matches("\\[\\d{2}.\\d{2}.\\d{2}, \\d{2}:\\d{2}:\\d{2}].*")) {

            String[] info = messageLine.split(" ", 4);
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

            if (!mediaInMessage && text.startsWith("\u200e")) {
                return null;
            } else {
                text = text.replaceFirst("\\u200e.*", "").trim();
            }

            messageStruct = new Message(text, sender, dateTime);

            lastMessage = messageStruct;

            messageListGes.add(messageStruct);
            chatterSet.add(sender);

        } else {
            if (lastMessage != null) {
                messageLine = messageLine.replaceFirst("\\u200e.*", "").trim();
                lastMessage.appendMessage(" " + messageLine);
            }
        }
        return lastMessage;
    }

    public List<Message> getMessageListGes() {
        return messageListGes;
    }

    public Set<String> getChatterSet() {
        return chatterSet;
    }
}