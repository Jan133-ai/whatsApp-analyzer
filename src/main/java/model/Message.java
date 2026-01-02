package model;

import java.time.LocalDateTime;

public class Message {
    
    private String text;
    private String sender;
    private LocalDateTime dateTime;
    private int words;
    private String[] wordsv;

    public Message (String text, String sender, LocalDateTime dateTime) {
        this.text = text;
        this.sender = sender;
        this.dateTime = dateTime;
        this.wordsv = text.split(" ");
        this.words = wordsv.length;
    }

    public void appendMessage(String message) {
        this.text = text.concat(message);
        this.wordsv = text.split(" ");
        this.words = wordsv.length;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public int getWords() {
        return words;
    }

    public int getDigga() {
        int diggaCounter = 0;
        for (String word : wordsv) {
            word = word.toLowerCase();
            if (word.contains("digga") || word.contains("diggah") || word.contains("digger")) {
                diggaCounter++;
            }
        }
        return diggaCounter;
    }
}
