package view.analyticsPanels;

import controller.analytics.OverallController;
import view.AnalyticsPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

public class OverallPanel extends AnalyticsPanel {

    OverallController overallController;

    public OverallPanel(File file) throws FileNotFoundException {

        super("Overall Data");

        overallController = new OverallController(file);
        String name = overallController.getName();

        JRadioButton[] radioButtons = addPieBarChoice();
        JRadioButton radioButtonBar = radioButtons[0];
        JRadioButton radioButtonPie = radioButtons[1];

        JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new GridLayout(0, 2, 0, 5));

        JPanel chartPanel2 = new JPanel();
        chartPanel2.setLayout(new GridLayout(0, 2, 0, 5));

        radioButtonBar.addActionListener(e -> {
            chartPanel.removeAll();

            addBarGraphFromMap(overallController.getMessageResults(), "Messages", name, "Sender",1, chartPanel);
            addBarGraphFromMap(overallController.getWordsResults(), "Words", name, "Sender", 1, chartPanel);
        });

        radioButtonPie.addActionListener(e -> {
            chartPanel.removeAll();

            addPieChartFromMap(overallController.getMessageResults(), "Messages", name, chartPanel);
            addPieChartFromMap(overallController.getWordsResults(), "Words", name, chartPanel);
        });

        addBarGraphFromMap(overallController.getMessageResults(), "Messages", name, "Sender", 1, chartPanel);
        addBarGraphFromMap(overallController.getWordsResults(), "Words", name, "Sender", 1, chartPanel);

        addBarGraphFromMap(overallController.getWordsPerMessageResults(), "Words/Message", name, "Sender", 1, chartPanel2);

        add(chartPanel);
        add(chartPanel2);
    }
}
