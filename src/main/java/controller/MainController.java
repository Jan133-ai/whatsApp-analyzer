package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import controller.analytics.OverallController;
import controller.analytics.YearlyController;
import controller.analytics.ChangeController;
import controller.analytics.AnswerController;
import controller.analytics.WeekdayController;
import controller.analytics.TimeController;
import controller.analytics.NextSenderController;
import controller.analytics.WordSearchController;
import model.ChatInfo;
import view.AnalyticsPanel;
import view.analyticsPanels.AnswerPanel;
import view.analyticsPanels.ChangePanel;
import view.analyticsPanels.NextSenderPanel;
import view.analyticsPanels.OverallPanel;
import view.analyticsPanels.TimePanel;
import view.analyticsPanels.WeekdayPanel;
import view.analyticsPanels.WordSearchPanel;
import view.analyticsPanels.YearlyPanel;

public class MainController {
    
    private ChatInfo currentChatInfo;
    private List<AnalyticsPanel> panelsList;

    public MainController(File file) throws FileNotFoundException{
        currentChatInfo = new ChatInfo(file);

        panelsList = new LinkedList<>();

        OverallController overallController = new OverallController(currentChatInfo);
        OverallPanel overallPanel = new OverallPanel(overallController);
        panelsList.add(overallPanel);

        YearlyController yearlyController = new YearlyController(currentChatInfo);
        YearlyPanel yearlyPanel = new YearlyPanel(yearlyController);
        panelsList.add(yearlyPanel);

        ChangeController changeController = new ChangeController(currentChatInfo);
        ChangePanel changePanel = new ChangePanel(changeController);
        panelsList.add(changePanel);

        AnswerController answerController = new AnswerController(currentChatInfo);
        AnswerPanel answerPanel = new AnswerPanel(answerController);
        panelsList.add(answerPanel);

        WeekdayController weekdayController = new WeekdayController(currentChatInfo);
        WeekdayPanel weekdayPanel = new WeekdayPanel(weekdayController);
        panelsList.add(weekdayPanel);

        TimeController timeController = new TimeController(currentChatInfo);
        TimePanel timePanel = new TimePanel(timeController);
        panelsList.add(timePanel);

        NextSenderController nextSenderController = new NextSenderController(currentChatInfo);
        NextSenderPanel nextSenderPanel = new NextSenderPanel(nextSenderController);
        panelsList.add(nextSenderPanel);

        WordSearchController wordSearchController = new WordSearchController(currentChatInfo);
        WordSearchPanel wordSearchPanel = new WordSearchPanel(wordSearchController);
        panelsList.add(wordSearchPanel);
    }

    public ChatInfo getCurrentChatInfo() {
        return currentChatInfo;
    }

    public List<AnalyticsPanel> getPanelsList() {
        return panelsList;
    }
}
