package model;

import java.time.LocalDateTime;
import java.util.Arrays;

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
        this.wordsv = buildWordsVec(text);
        this.words = wordsv.length;
    }

    public void appendMessage(String message) {
        this.text = text.concat(" " + message);
        this.wordsv = buildWordsVec(text);
        this.words = wordsv.length;
    }

    private String[] buildWordsVec(String text) {
        String[] vec = text.split("[\\p{P}\\s&&[^-]]");
        return Arrays.stream(vec)
            .filter(s -> s != null && !s.isBlank())
            .toArray(String[]::new);
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

    public String[] getWordsv() {
        return wordsv;
    }

    public int searchWord(String searchedWord) {
        int wordCounter = 0;
        for (String word : wordsv) {
            if (word.equalsIgnoreCase(searchedWord)) {
                wordCounter++;
            }
        }
        return wordCounter;
    }
}
