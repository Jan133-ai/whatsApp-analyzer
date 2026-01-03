package view;

import view.analyticsPanels.OverallPanel;
import view.analyticsPanels.WeekdayPanel;
import view.analyticsPanels.YearlyPanel;

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
    }
}
