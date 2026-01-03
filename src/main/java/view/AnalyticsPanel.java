package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public abstract class AnalyticsPanel extends JPanel {

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, getPreferredSize().height);
    }

    public <T extends Number> void addBarGraphFromMap(Map<String, T> map, String title, String name, String xLabel, JPanel panel) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, T> me : map.entrySet()) {
            if (!me.getKey().equals("Total")) {
                dataset.addValue(me.getValue(), me.getKey(), title);
            }
        }

        JFreeChart barChart = ChartFactory.createBarChart(title + ": " + name,
                xLabel, title, dataset);

        ChartPanel chPanel = new ChartPanel(barChart);
        chPanel.setPreferredSize(new Dimension(500, 350));
        chPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 350));
        chPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(chPanel);
        panel.validate();
    }

    public <T extends Number> void addPieChartFromMap(Map<String, T> map, String title, String name, JPanel panel) {

        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        for (Map.Entry<String, T> me : map.entrySet()) {
            if (!me.getKey().equals("Total")) {
                dataset.setValue(me.getKey(), me.getValue());
            }
        }

        JFreeChart pieChart = ChartFactory.createPieChart(title + ": " + name, dataset);

        ChartPanel chPanel = new ChartPanel(pieChart);
        chPanel.setPreferredSize(new Dimension(500, 350));
        chPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 350));
        chPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(chPanel);
        panel.validate();
    }

    public JRadioButton[] addPieBarChoice() {
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

        return new JRadioButton[]{radioButtonBar, radioButtonPie};
    }
}
