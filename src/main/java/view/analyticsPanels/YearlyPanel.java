package view.analyticsPanels;

import controller.analytics.YearlyController;
import view.AnalyticsPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.Year;

public class YearlyPanel extends AnalyticsPanel {

    public YearlyPanel(File file) throws FileNotFoundException {

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
            chartPanel.removeAll();

            addBarGraphFromMap(yearlyController.getYearlyMessages(year), "Messages", name, chartPanel);
            addBarGraphFromMap(yearlyController.getYearlyWords(year), "Words", name, chartPanel);
        });

        add(yearComboBox);

        addBarGraphFromMap(yearlyController.getYearlyMessages(Year.now()), "Messages", name, chartPanel);
        addBarGraphFromMap(yearlyController.getYearlyWords(Year.now()), "Words", name, chartPanel);

        add(chartPanel);
    }
}
