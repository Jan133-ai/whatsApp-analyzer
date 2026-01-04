package view.analyticsPanels;

import controller.analytics.ChangeController;
import view.AnalyticsPanel;
import view.enums.TIMESPAN;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.Year;
import java.time.format.DateTimeFormatter;

public class ChangePanel extends AnalyticsPanel {

    ChangeController changeController;

    public ChangePanel(File file) throws FileNotFoundException {
        super("Change over Time");

        changeController = new ChangeController(file);

        JPanel chartPanel = new JPanel();

        JComboBox<TIMESPAN> timespanComboBox = new JComboBox<>();
        for (TIMESPAN time : TIMESPAN.values()) {
            timespanComboBox.addItem(time);
        }
        timespanComboBox.addActionListener(e -> {
            JComboBox cb = (JComboBox)e.getSource();
            TIMESPAN timespan = (TIMESPAN) cb.getSelectedItem();

            chartPanel.removeAll();

            addTimeSeriesChartFromMap(changeController.getMessageOverTimespan(timespan),
                    timespan, "Messages", chartPanel);
        });

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        JLabel label = new JLabel("Last Message was on " + changeController.getMostRecentMessage().format(formatter));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        timespanComboBox.setMaximumSize(timespanComboBox.getPreferredSize());

        addTimeSeriesChartFromMap(changeController.getMessageOverTimespan(TIMESPAN.LAST30DAYS),
                TIMESPAN.LAST30DAYS, "Messages", chartPanel);

        add(timespanComboBox);
        add(label);
        add(chartPanel);
    }
}
