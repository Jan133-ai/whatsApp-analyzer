package view.analyticsPanels;

import controller.analytics.WeekdayController;
import view.AnalyticsPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

public class WeekdayPanel extends AnalyticsPanel {

    WeekdayController weekdayController;

    Set<String> selectedChatters;

    boolean showPieChart;

    public WeekdayPanel(File file) throws FileNotFoundException {

        weekdayController = new WeekdayController(file);

        showPieChart = false;

        String name = weekdayController.getName();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Weekday Data");
        Font labelFont = titleLabel.getFont();
        titleLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(titleLabel);

        JRadioButton[] radioButtons = addPieBarChoice();

        JRadioButton radioButtonBar = radioButtons[0];
        JRadioButton radioButtonPie = radioButtons[1];



        JPanel chekBoxesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        chekBoxesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Set<String> chatters = weekdayController.getChatters();

        selectedChatters =  chatters;

        JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new GridLayout(0, 2, 0, 5));

        radioButtonBar.addActionListener(e -> {
            chartPanel.removeAll();

            showPieChart = false;

            addBarGraphFromMap(weekdayController.getWeekdaysMessages(selectedChatters),
                    "Messages by Weekday", name, "Weekday", chartPanel);
            addBarGraphFromMap(weekdayController.getWeekdaysWords(selectedChatters),
                    "Messages by Weekday", name, "Weekday", chartPanel);
        });

        radioButtonPie.addActionListener(e -> {
            chartPanel.removeAll();

            showPieChart = true;

            addPieChartFromMap(weekdayController.getWeekdaysMessages(selectedChatters),
                    "Messages by Weekday", name, chartPanel);
            addPieChartFromMap(weekdayController.getWeekdaysWords(selectedChatters),
                    "Messages by Weekday", name, chartPanel);
        });

        for (String chatter : chatters) {
            JCheckBox checkBox = new JCheckBox(chatter, true);
            checkBox.addActionListener(e -> {
                JCheckBox cb = (JCheckBox)e.getSource();
                if (cb.isSelected()) {
                    selectedChatters.add(chatter);
                }
                else {
                    selectedChatters.remove(chatter);
                }

                chartPanel.removeAll();

                if (showPieChart) {
                    addPieChartFromMap(weekdayController.getWeekdaysMessages(selectedChatters),
                            "Messages by Weekday", name, chartPanel);
                    addPieChartFromMap(weekdayController.getWeekdaysWords(selectedChatters),
                            "Messages by Weekday", name, chartPanel);
                } else {
                    addBarGraphFromMap(weekdayController.getWeekdaysMessages(selectedChatters),
                            "Messages by Weekday", name, "Weekday", chartPanel);
                    addBarGraphFromMap(weekdayController.getWeekdaysWords(selectedChatters),
                            "Messages by Weekday", name, "Weekday", chartPanel);
                }
            });

            chekBoxesPanel.add(checkBox);
        }

        add(chekBoxesPanel);

        addBarGraphFromMap(weekdayController.getWeekdaysMessages(selectedChatters),
                "Messages by Weekday", name, "Weekday", chartPanel);
        addBarGraphFromMap(weekdayController.getWeekdaysWords(selectedChatters),
                "Messages by Weekday", name, "Weekday", chartPanel);

        add(chartPanel);
    }

}
