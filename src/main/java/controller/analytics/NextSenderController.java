package controller.analytics;

import controller.AnalyticsController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;

public class NextSenderController extends AnalyticsController {

    Map<String, Map<String, Integer>> nextSendersMap;
    String[] chatterArray;

    public NextSenderController(File file) throws FileNotFoundException {
        super(file);

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
