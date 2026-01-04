package controller.analytics;

import controller.AnalyticsController;
import model.MessageListFilter;
import org.jfree.data.xy.DefaultXYZDataset;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.util.*;

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

    public DefaultXYZDataset getTimeWeekdayMessages(Set<String> selectedChatters) {

        DefaultXYZDataset dataset = new DefaultXYZDataset();

        List<Double> xList = new ArrayList<>();
        List<Double> yList = new ArrayList<>();
        List<Double> zList = new ArrayList<>();

        for (int i =0; i < 24; i++) {
            for (DayOfWeek day : DayOfWeek.values()) {
                Integer messageCount = 0;
                Map<String, Integer> messageResults = currentChatInfo.callMessages(
                        new MessageListFilter(null, String.valueOf(i), day));
                for (Map.Entry<String, Integer> entry : messageResults.entrySet()) {
                    if (selectedChatters.contains(entry.getKey())) {
                        messageCount += entry.getValue();
                    }
                }
                xList.add((double) i);
                yList.add((double) day.getValue() - 1);
                zList.add((double) messageCount);
            }
        }
        dataset.addSeries("Heatmap", new double[][] {
                xList.stream().mapToDouble(Double::doubleValue).toArray(),
                yList.stream().mapToDouble(Double::doubleValue).toArray(),
                zList.stream().mapToDouble(Double::doubleValue).toArray()
        });

        return dataset;
    }
}
