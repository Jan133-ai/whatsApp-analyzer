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

        WeekdayPanel weekdayPanel = new WeekdayPanel(file);
        add(weekdayPanel);

        TimePanel timePanel = new TimePanel(file);
        add(timePanel);

        ChangePanel changePanel = new ChangePanel(file);
        add(changePanel);

        NextSenderPanel nextSenderPanel = new NextSenderPanel(file);
        add(nextSenderPanel);
    }
}
