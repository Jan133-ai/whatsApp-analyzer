package controller.analytics;

import controller.AnalyticsController;
import model.MessageListFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class TimeController extends AnalyticsController {

    public TimeController(File file) throws FileNotFoundException {
        super(file);
    }

    public Map<String, Integer> getTimeMessages(Set<String> selectedChatters) {
        Map<String, Integer> timeMessages = new LinkedHashMap<>();

        for (int i =0; i < 24; i++) {
            Integer messageCount = 0;
            Map<String, Integer> messageResults = currentChatInfo.callMessages(
                    new MessageListFilter(null, String.valueOf(i), null));
            for (Map.Entry<String, Integer> entry : messageResults.entrySet()) {
                if (selectedChatters.contains(entry.getKey())) {
                    messageCount += entry.getValue();
                }
            }
            timeMessages.put(String.valueOf(i) + ":00-" + String.valueOf(i) + ":59", messageCount);
        }
        return timeMessages;
    }
}
