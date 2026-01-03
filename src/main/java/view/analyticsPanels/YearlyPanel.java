package view.analyticsPanels;

import controller.analytics.YearlyController;
import view.AnalyticsPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.Year;

public class YearlyPanel extends AnalyticsPanel {

    private Year selectedYear;
    private boolean showPieCHart;

    public YearlyPanel(File file) throws FileNotFoundException {

        selectedYear = Year.now();

        showPieCHart = false;

        YearlyController yearlyController = new YearlyController(file);

        String name = yearlyController.getName();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Yearly Data");
        Font labelFont = titleLabel.getFont();
        titleLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(titleLabel);

        JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new GridLayout(0, 2, 0, 5));

        JComboBox<Year> yearComboBox = new JComboBox<>();
        for(int y = Year.now().getValue(); y >= 2009; y--) {
            yearComboBox.addItem(Year.of(y));
        }

        yearComboBox.setMaximumSize(yearComboBox.getPreferredSize());

        yearComboBox.addActionListener(e -> {
            JComboBox cb = (JComboBox)e.getSource();
            Year year = (Year) cb.getSelectedItem();

            assert year != null;
            selectedYear = year;

            chartPanel.removeAll();

            if(showPieCHart) {
                addPieChartFromMap(yearlyController.getYearlyMessages(year),
                        "Messages " + year.toString(), name, chartPanel);
                addPieChartFromMap(yearlyController.getYearlyWords(year),
                        "Words " + year.toString(), name, chartPanel);
            } else {
                addBarGraphFromMap(yearlyController.getYearlyMessages(year),
                        "Messages " + year.toString(), name, chartPanel);
                addBarGraphFromMap(yearlyController.getYearlyWords(year),
                        "Words " + year.toString(), name, chartPanel);
            }
        });

        add(yearComboBox);

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

        radioButtonBar.addActionListener(e -> {
            chartPanel.removeAll();

            showPieCHart = false;

            addBarGraphFromMap(yearlyController.getYearlyMessages(selectedYear),
                    "Messages " + selectedYear.toString(), name, chartPanel);
            addBarGraphFromMap(yearlyController.getYearlyWords(selectedYear),
                    "Words " + selectedYear.toString(), name, chartPanel);
        });


        radioButtonPie.addActionListener(e -> {
            chartPanel.removeAll();

            showPieCHart = true;

            addPieChartFromMap(yearlyController.getYearlyMessages(selectedYear),
                    "Messages " + selectedYear.toString(), name, chartPanel);
            addPieChartFromMap(yearlyController.getYearlyWords(selectedYear),
                    "Words " + selectedYear.toString(), name, chartPanel);
        });

        addBarGraphFromMap(yearlyController.getYearlyMessages(Year.now()),
                "Messages  " + Year.now().toString(), name, chartPanel);
        addBarGraphFromMap(yearlyController.getYearlyWords(Year.now()),
                "Words " + Year.now().toString(), name, chartPanel);

        add(chartPanel);
    }
}
