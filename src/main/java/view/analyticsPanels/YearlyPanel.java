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
    private boolean showPieChart;

    public YearlyPanel(File file) throws FileNotFoundException {

        super("Yearly Data");

        selectedYear = Year.now();
        showPieChart = false;

        YearlyController yearlyController = new YearlyController(file);
        String name = yearlyController.getName();

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

            if(showPieChart) {
                addPieChartFromMap(yearlyController.getYearlyMessages(year),
                        "Messages " + year.toString(), name, chartPanel);
                addPieChartFromMap(yearlyController.getYearlyWords(year),
                        "Words " + year.toString(), name, chartPanel);
            } else {
                addBarGraphFromMap(yearlyController.getYearlyMessages(year),
                        "Messages " + year.toString(), name, "Sender", 1, chartPanel);
                addBarGraphFromMap(yearlyController.getYearlyWords(year),
                        "Words " + year.toString(), name, "Sender", 1, chartPanel);
            }
        });

        add(yearComboBox);

        JRadioButton[] radioButtons = addPieBarChoice();

        JRadioButton radioButtonBar = radioButtons[0];
        JRadioButton radioButtonPie = radioButtons[1];

        radioButtonBar.addActionListener(e -> {
            chartPanel.removeAll();

            showPieChart = false;

            addBarGraphFromMap(yearlyController.getYearlyMessages(selectedYear),
                    "Messages " + selectedYear.toString(), name, "Sender", 1, chartPanel);
            addBarGraphFromMap(yearlyController.getYearlyWords(selectedYear),
                    "Words " + selectedYear.toString(), name, "Sender", 1, chartPanel);
        });


        radioButtonPie.addActionListener(e -> {
            chartPanel.removeAll();

            showPieChart = true;

            addPieChartFromMap(yearlyController.getYearlyMessages(selectedYear),
                    "Messages " + selectedYear.toString(), name, chartPanel);
            addPieChartFromMap(yearlyController.getYearlyWords(selectedYear),
                    "Words " + selectedYear.toString(), name, chartPanel);
        });

        addBarGraphFromMap(yearlyController.getYearlyMessages(Year.now()),
                "Messages  " + Year.now().toString(), name, "Sender", 1, chartPanel);
        addBarGraphFromMap(yearlyController.getYearlyWords(Year.now()),
                "Words " + Year.now().toString(), name, "Sender", 1, chartPanel);

        add(chartPanel);
    }
}
