package view.analyticsPanels;

import controller.analytics.OverallController;
import view.AnalyticsPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class OverallPanel extends AnalyticsPanel {

    OverallController overallController;

    public OverallPanel(File file) throws FileNotFoundException {

        overallController = new OverallController(file);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Overall Data");
        Font labelFont = titleLabel.getFont();
        titleLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(titleLabel);

        JRadioButton[] radioButtons = addPieBarChoice();

        JRadioButton radioButtonBar = radioButtons[0];
        JRadioButton radioButtonPie = radioButtons[1];

        JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new GridLayout(0, 2, 0, 5));

        String name = overallController.getName();

        radioButtonBar.addActionListener(e -> {
            chartPanel.removeAll();

            addBarGraphFromMap(overallController.getMessageResults(), "Messages", name, "Sender", chartPanel);
            addBarGraphFromMap(overallController.getWordsResults(), "Words", name, "Sender", chartPanel);
        });


        radioButtonPie.addActionListener(e -> {
            chartPanel.removeAll();

            addPieChartFromMap(overallController.getMessageResults(), "Messages", name, chartPanel);
            addPieChartFromMap(overallController.getWordsResults(), "Words", name, chartPanel);
        });

        addBarGraphFromMap(overallController.getMessageResults(), "Messages", name, "Sender", chartPanel);
        addBarGraphFromMap(overallController.getWordsResults(), "Words", name, "Sender", chartPanel);

        JPanel chartPanel2 = new JPanel();
        chartPanel2.setLayout(new GridLayout(0, 2, 0, 5));

        addBarGraphFromMap(overallController.getWordsPerMessageResults(), "Words/Message", name, "Sender", chartPanel2);

        add(chartPanel);
        add(chartPanel2);
    }
}
