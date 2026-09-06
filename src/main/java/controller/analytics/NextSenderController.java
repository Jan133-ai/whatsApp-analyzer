package controller.analytics;

import controller.AnalyticsController;
import model.ChatInfo;

import java.io.FileNotFoundException;
import java.util.Map;

public class NextSenderController extends AnalyticsController {

    Map<String, Map<String, Integer>> nextSendersMap;
    String[] chatterArray;

    public NextSenderController(ChatInfo currentChatInfo) throws FileNotFoundException {
        super(currentChatInfo);

        nextSendersMap = currentChatInfo.getNextSenders();

        chatterArray = currentChatInfo.getChatterSet().toArray(new String[0]);
    }

    public Map<String, Map<String, Integer>> getNextSendersMap() {
        return nextSendersMap;
    }

    public String[] getChatterArray() {
        return chatterArray;
    }
}
