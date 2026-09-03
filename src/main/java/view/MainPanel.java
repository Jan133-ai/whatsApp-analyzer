package view;

import view.analyticsPanels.*;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;

public class MainPanel extends JPanel {

    public MainPanel(File file) throws FileNotFoundException {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        OverallPanel overallPanel = new OverallPanel(file);
        add(overallPanel);

        YearlyPanel yearlyPanel = new YearlyPanel(file);
        add(yearlyPanel);

        ChangePanel changePanel = new ChangePanel(file);
        add(changePanel);

        AnswerPanel answerPanel = new AnswerPanel(file);
        add(answerPanel);

        WeekdayPanel weekdayPanel = new WeekdayPanel(file);
        add(weekdayPanel);

        TimePanel timePanel = new TimePanel(file);
        add(timePanel);

        NextSenderPanel nextSenderPanel = new NextSenderPanel(file);
        add(nextSenderPanel);

        WordSearchPanel wordSearchPanel = new WordSearchPanel(file);
        add(wordSearchPanel);
    }
}
