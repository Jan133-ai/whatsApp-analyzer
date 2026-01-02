package controller;

import model.ChatInfo;
import model.MessageListFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

public class AnalyticsController {

    ChatInfo currentChatInfo;
    String name;
    MessageListFilter nullFilter;

    Map<String, Integer> messageResults;
    Map<String, Integer> wordsResults;
    Map<String, Float> wordsPerMessageResults;

    public  AnalyticsController(File file) throws FileNotFoundException {

        nullFilter = new MessageListFilter(null, null, null);

        currentChatInfo = new ChatInfo(file);

        name = currentChatInfo.getName();

        messageResults = currentChatInfo.callMessages(nullFilter);
        wordsResults = currentChatInfo.callWords(nullFilter);
        wordsPerMessageResults = currentChatInfo.callWordsPerMessage(nullFilter);
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getMessageResults() {
        return messageResults;
    }

    public Map<String, Integer> getWordsResults() {
        return wordsResults;
    }

    public Map<String, Float> getWordsPerMessageResults() {
        return wordsPerMessageResults;
    }
}
