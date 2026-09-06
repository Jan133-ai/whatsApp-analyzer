package controller.analytics;

import java.io.FileNotFoundException;
import java.util.Map;

import controller.AnalyticsController;
import model.ChatInfo;

public class WordSearchController extends AnalyticsController{

    public WordSearchController(ChatInfo currentChatInfo) throws FileNotFoundException {
        super(currentChatInfo);
    }

    public Map<String, Integer> searchWord(String searchedWord) {
        return currentChatInfo.callSearchWord(nullFilter, searchedWord);
    }
}
