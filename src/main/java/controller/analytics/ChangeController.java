package controller.analytics;

import controller.AnalyticsController;
import model.MessageListFilter;
import view.enums.TIMESPAN;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class ChangeController extends AnalyticsController {

    private final LocalDate mostRecentMessage;
    private final LocalDate firstMessage;

    public ChangeController(File file) throws FileNotFoundException {
        super(file);

        mostRecentMessage = currentChatInfo.getMostRecentMessageDate();
        firstMessage = currentChatInfo.getFirstMessageDate();
    }

    public Map<String, double[][]> getDataOverTimespan(TIMESPAN timespan, boolean messagesBool) {
        Map<String, double[][]> messageOverTimespanMap = new HashMap<>();
        if (timespan == TIMESPAN.LAST30DAYS) {
            for (int i = 0; i < 30; i++) {
                LocalDate date = mostRecentMessage.minusDays(i);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String dateString = date.format(formatter);
                addToMapFromDateString(dateString, messageOverTimespanMap, date, i, 30, messagesBool);
            }
        } else if (timespan == TIMESPAN.LASTYEAR) {
            for (int i = 0; i < 12; i++) {
                LocalDate date = mostRecentMessage.minusMonths(i)
                        .minusDays(mostRecentMessage.getDayOfMonth() - 2);
                int month = date.getMonth().getValue();
                int year = date.getYear();
                String monthString = String.valueOf(month) + "." + String.valueOf(year);

                addToMapFromDateString(monthString, messageOverTimespanMap, date, i, 12, messagesBool);
            }
        } else {
            int numberOfMonths = (int)ChronoUnit.MONTHS.between(firstMessage, mostRecentMessage);
            if  (numberOfMonths <= 200) {
                for (int i = 0; i <= numberOfMonths; i++) {
                    LocalDate date = mostRecentMessage.minusMonths(i)
                            .minusDays(mostRecentMessage.getDayOfMonth() - 2);
                    int month = date.getMonth().getValue();
                    int year = date.getYear();
                    String monthString = String.valueOf(month) + "." + String.valueOf(year);

                    addToMapFromDateString(monthString, messageOverTimespanMap, date, i,
                            numberOfMonths + 1, messagesBool);
                }
            } else {
                int numberOfYears = (int)ChronoUnit.YEARS.between(firstMessage, mostRecentMessage);
                for (int i = 0; i <= numberOfYears; i++) {
                    LocalDate date = mostRecentMessage.minusYears(i)
                            .minusDays(mostRecentMessage.getDayOfMonth() - 2);
                    String yearString = String.valueOf(date.getYear());

                    addToMapFromDateString(yearString, messageOverTimespanMap, date, i,
                            numberOfYears + 1, messagesBool);
                }
            }
        }
        return messageOverTimespanMap;
    }

    private void addToMapFromDateString(String dateString, Map<String, double[][]> messageOverTimespanMap,
                                        LocalDate date, int i, int numberOfDataPoints, boolean messagesBool) {
        MessageListFilter dateFilter = new MessageListFilter(dateString, null, null);

        Map<String, Integer> dateMap;
        if (messagesBool) {
            dateMap = currentChatInfo.callMessages(dateFilter);
        } else {
            dateMap = currentChatInfo.callWords(dateFilter);
        }


        for (Map.Entry<String, Integer> entry : dateMap.entrySet()) {
            if (!messageOverTimespanMap.containsKey(entry.getKey())) {
                messageOverTimespanMap.put(entry.getKey(), new double[2][numberOfDataPoints]);
            }
            messageOverTimespanMap.get(entry.getKey())[0][i] = date
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli();
            messageOverTimespanMap.get(entry.getKey())[1][i] = entry.getValue();
        }
    }

    public LocalDate getMostRecentMessage() {
        return mostRecentMessage;
    }
}
