package controller;

import model.ChatInfo;
import model.MessageListFilter;

import java.io.FileNotFoundException;
import java.util.Set;

public abstract class AnalyticsController {

    protected ChatInfo currentChatInfo;
    protected String name;
    protected MessageListFilter nullFilter;
    protected Set<String> chatters;

    public  AnalyticsController(ChatInfo currentChatInfo) throws FileNotFoundException {

        nullFilter = new MessageListFilter(null, null, null);

        this.currentChatInfo = currentChatInfo;

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
