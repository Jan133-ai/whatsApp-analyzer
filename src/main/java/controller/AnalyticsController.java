package controller;

import model.ChatInfo;
import model.MessageListFilter;

import java.io.File;
import java.io.FileNotFoundException;

public abstract class AnalyticsController {

    protected ChatInfo currentChatInfo;
    protected String name;
    protected MessageListFilter nullFilter;

    public  AnalyticsController(File file) throws FileNotFoundException {

        nullFilter = new MessageListFilter(null, null, null);

        currentChatInfo = new ChatInfo(file);

        name = currentChatInfo.getName();
    }

    public String getName() {
        return name;
    }
}
