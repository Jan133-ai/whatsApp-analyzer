package controller.analytics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import controller.AnalyticsController;

public class WordSearchController extends AnalyticsController{

    public WordSearchController(File file) throws FileNotFoundException {
        super(file);
    }

    public Map<String, Integer> searchWord(String searchedWord) {
        return currentChatInfo.callSearchWord(nullFilter, searchedWord);
    }
}
