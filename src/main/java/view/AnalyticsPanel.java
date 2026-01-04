package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.DefaultXYDataset;
import view.enums.TIMESPAN;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.Map;

public abstract class AnalyticsPanel extends JPanel {

    public AnalyticsPanel(String title) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel(title);
        Font labelFont = titleLabel.getFont();
        titleLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(titleLabel);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, getPreferredSize().height);
    }

    public <T extends Number> void addBarGraphFromMap(Map<String, T> map, String title, String name, String xLabel, int width, JPanel panel) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, T> me : map.entrySet()) {
            if (!me.getKey().equals("Total")) {
                dataset.addValue(me.getValue(), me.getKey(), title);
            }
        }

        JFreeChart barChart = ChartFactory.createBarChart(title + ": " + name,
                xLabel, title, dataset);
        barChart.getCategoryPlot().setBackgroundPaint(Color.WHITE);
        barChart.getCategoryPlot().setRangeGridlinesVisible(true);
        barChart.getCategoryPlot().setRangeGridlinePaint(Color.BLACK);

        ChartPanel chPanel = new ChartPanel(barChart);
        chPanel.setPreferredSize(new Dimension(500 * width, 350));
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
        pieChart.getPlot().setBackgroundPaint(Color.WHITE);

        ChartPanel chPanel = new ChartPanel(pieChart);
        chPanel.setPreferredSize(new Dimension(500, 350));
        chPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 350));
        chPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(chPanel);
        panel.validate();
    }

    public void addTimeSeriesChartFromMap(Map<String, double[][]> map, TIMESPAN timespan, String title, JPanel panel) {
        DefaultXYDataset dataset = new DefaultXYDataset();
        for (Map.Entry<String, double[][]> me : map.entrySet()) {
            if (!me.getKey().equals("Total")) {
                dataset.addSeries(me.getKey(), me.getValue());
            }
        }

        JFreeChart timeSeriesChart = ChartFactory.createTimeSeriesChart(title + " over Time",
                "Date", title, dataset);
        XYPlot plot = timeSeriesChart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        XYLineAndShapeRenderer renderer =
                (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setAutoPopulateSeriesStroke(false);
        renderer.setDefaultStroke(new BasicStroke(3f));
        renderer.setDefaultShapesVisible(false);

        DateAxis axis = (DateAxis) timeSeriesChart.getXYPlot().getDomainAxis();
        if (timespan == TIMESPAN.LAST30DAYS) {
            axis.setDateFormatOverride(new SimpleDateFormat("dd.MM.yyyy"));
        } else {
            axis.setDateFormatOverride(new SimpleDateFormat("MM.yyyy"));
        }

        ChartPanel chPanel = new ChartPanel(timeSeriesChart);
        chPanel.setPreferredSize(new Dimension(1000, 350));
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
