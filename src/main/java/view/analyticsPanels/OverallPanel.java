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

        overallController = new OverallController(file);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Overall Data");
        Font labelFont = titleLabel.getFont();
        titleLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(titleLabel);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JRadioButton radioButtonBar = new JRadioButton("Bar Chart");
        radioButtonBar.setSelected(true);
        JRadioButton radioButtonPie = new JRadioButton("Pie Chart");

        ButtonGroup choice = new ButtonGroup();
        choice.add(radioButtonBar);
        choice.add(radioButtonPie);

        buttonsPanel.add(radioButtonBar);
        buttonsPanel.add(radioButtonPie);

        add(buttonsPanel);

        JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new GridLayout(0, 2, 0, 5));

        String name = overallController.getName();

        radioButtonBar.addActionListener(e -> {
            chartPanel.removeAll();

            addBarGraphFromMap(overallController.getMessageResults(), "Messages", name, chartPanel);
            addBarGraphFromMap(overallController.getWordsResults(), "Words", name, chartPanel);
        });


        radioButtonPie.addActionListener(e -> {
            chartPanel.removeAll();

            addPieChartFromMap(overallController.getMessageResults(), "Messages", name, chartPanel);
            addPieChartFromMap(overallController.getWordsResults(), "Words", name, chartPanel);
        });

        addBarGraphFromMap(overallController.getMessageResults(), "Messages", name, chartPanel);
        addBarGraphFromMap(overallController.getWordsResults(), "Words", name, chartPanel);

        JPanel chartPanel2 = new JPanel();
        chartPanel2.setLayout(new GridLayout(0, 2, 0, 5));

        addBarGraphFromMap(overallController.getWordsPerMessageResults(), "Words/Message", name, chartPanel2);

        add(chartPanel);
        add(chartPanel2);
    }
}
