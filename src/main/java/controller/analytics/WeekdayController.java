package controller.analytics;

import controller.AnalyticsController;
import model.MessageListFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class WeekdayController extends AnalyticsController {

    private final Set<String> chatters;

    public WeekdayController(File file) throws FileNotFoundException {
        super(file);

        chatters = currentChatInfo.getChatterSet();
    }

    public Map<String, Integer> getWeekdaysMessages(Set<String> selectedChatters) {
        Map<String, Integer> weekdaysMessages = new LinkedHashMap<>();

        for (DayOfWeek day : DayOfWeek.values()) {
            Integer messageCount = 0;
            Map<String, Integer> messageResults = currentChatInfo.callMessages(
                    new MessageListFilter(null, null, day));
            for (Map.Entry<String, Integer> entry : messageResults.entrySet()) {
                if (selectedChatters.contains(entry.getKey())) {
                    messageCount += entry.getValue();
                }
            }
            weekdaysMessages.put(day.toString(), messageCount);
        }

        return weekdaysMessages;
    }

    public Map<String, Integer> getWeekdaysWords(Set<String> selectedChatters) {
        Map<String, Integer> weekdaysWords = new LinkedHashMap<>();

        for (DayOfWeek day : DayOfWeek.values()) {
            Integer wordCount = 0;
            Map<String, Integer> wordResults = currentChatInfo.callWords(
                    new MessageListFilter(null, null, day));
            for (Map.Entry<String, Integer> entry : wordResults.entrySet()) {
                if (selectedChatters.contains(entry.getKey())) {
                    wordCount += entry.getValue();
                }
            }
            weekdaysWords.put(day.toString(), wordCount);
        }

        return weekdaysWords;
    }

    public Set<String> getChatters() {
        return chatters;
    }
}
