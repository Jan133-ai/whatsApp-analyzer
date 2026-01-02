package view.analyticsPanels;

import view.AnalyticsPanel;
import controller.AnalyticsController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

public class OverallPanel extends AnalyticsPanel {

    public OverallPanel(File file) throws FileNotFoundException {

        analyticsController = new AnalyticsController(file);

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

        radioButtonBar.addActionListener(e -> {
            chartPanel.removeAll();

            addBarGraphFromMap(analyticsController.getMessageResults(), "Messages", chartPanel);
            addBarGraphFromMap(analyticsController.getWordsResults(), "Words", chartPanel);
        });


        radioButtonPie.addActionListener(e -> {
            chartPanel.removeAll();

            addPieChartFromMap(analyticsController.getMessageResults(), "Messages", chartPanel);
            addPieChartFromMap(analyticsController.getWordsResults(), "Words", chartPanel);
        });

        addBarGraphFromMap(analyticsController.getMessageResults(), "Messages", chartPanel);
        addBarGraphFromMap(analyticsController.getWordsResults(), "Words", chartPanel);

        JPanel chartPanel2 = new JPanel();
        chartPanel2.setLayout(new GridLayout(0, 2, 0, 5));

        addBarGraphFromMap(analyticsController.getWordsPerMessageResults(), "Words/Message", chartPanel2);

        add(chartPanel);
        add(chartPanel2);
    }
}
