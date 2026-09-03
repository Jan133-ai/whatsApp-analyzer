package view.analyticsPanels;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import controller.analytics.AnswerController;
import view.AnalyticsPanel;

public class AnswerPanel extends AnalyticsPanel {

    AnswerController answerController;
    String name;

    public AnswerPanel(File file) throws FileNotFoundException {

        super("Answer Time");

        answerController = new AnswerController(file);
        name = answerController.getName();

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JRadioButton radioButtonAbs = new JRadioButton("Absolute Answers");
        radioButtonAbs.setSelected(true);
        JRadioButton radioButtonPerc = new JRadioButton("Answer Percentage");

        ButtonGroup choice = new ButtonGroup();
        choice.add(radioButtonAbs);
        choice.add(radioButtonPerc);

        buttonsPanel.add(radioButtonAbs);
        buttonsPanel.add(radioButtonPerc);

        add(buttonsPanel);

        JPanel chartPanel = new JPanel();

        radioButtonAbs.addActionListener(e -> {
            chartPanel.removeAll();
            
            addBarGraphFromLabelMap(answerController.getAnswerIntevalMap(), "Absolute Answers", name, "Answer time between (hh:mm)", 2, chartPanel);
        });

        radioButtonPerc.addActionListener(e -> {
            chartPanel.removeAll();
            
            addBarGraphFromLabelMap(answerController.getAnswerPercentIntevalMap(), "Answer Percentage", name, "Answer time between (hh:mm)", 2, chartPanel);
        });

        addBarGraphFromLabelMap(answerController.getAnswerIntevalMap(), "Absolute Answers", name, "Answer time between (hh:mm)", 2, chartPanel);

        add(chartPanel);
    }
    
}
