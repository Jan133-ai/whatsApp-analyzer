package controller.analytics;

import controller.AnalyticsController;
import model.MessageListFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Year;
import java.util.Map;

public class YearlyController extends AnalyticsController {


    public YearlyController(File file) throws FileNotFoundException {
        super(file);
    }

    public Map<String, Integer> getYearlyMessages(Year year) {
        MessageListFilter yearFilter = new MessageListFilter(year.toString(), null, null);

        return currentChatInfo.callMessages(yearFilter);
    }

    public Map<String, Integer> getYearlyWords(Year year) {
        MessageListFilter yearFilter = new MessageListFilter(year.toString(), null, null);

        return currentChatInfo.callWords(yearFilter);
    }
}
