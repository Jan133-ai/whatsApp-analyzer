package controller.analytics;

import controller.AnalyticsController;
import model.ChatInfo;

import java.io.FileNotFoundException;
import java.util.Map;

public class OverallController extends AnalyticsController {

    Map<String, Integer> messageResults;
    Map<String, Integer> wordsResults;
    Map<String, Float> wordsPerMessageResults;

    public OverallController(ChatInfo currentChatInfo) throws FileNotFoundException {
        super(currentChatInfo);
        messageResults = currentChatInfo.callMessages(nullFilter);
        wordsResults = currentChatInfo.callWords(nullFilter);
        wordsPerMessageResults = currentChatInfo.callWordsPerMessage(nullFilter);
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
