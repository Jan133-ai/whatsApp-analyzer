package controller;

import model.ChatInfo;
import model.MessageListFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

public abstract class AnalyticsController {

    protected ChatInfo currentChatInfo;
    protected String name;
    protected MessageListFilter nullFilter;
    protected Set<String> chatters;

    public  AnalyticsController(File file) throws FileNotFoundException {

        nullFilter = new MessageListFilter(null, null, null);

        currentChatInfo = new ChatInfo(file);

        name = currentChatInfo.getName();

        chatters = currentChatInfo.getChatterSet();
    }

    public String getName() {
        return name;
    }

    public Set<String> getChatters() {
        return chatters;
    }
}
