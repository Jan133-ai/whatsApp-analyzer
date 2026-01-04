package view.analyticsPanels;

import controller.analytics.TimeController;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.GrayPaintScale;
import org.jfree.chart.renderer.PaintScale;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.xy.DefaultXYZDataset;
import view.AnalyticsPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.time.DayOfWeek;
import java.util.Set;

public class TimePanel extends AnalyticsPanel {

    TimeController timeController;
    Set<String> selectedChatters;

    public TimePanel(File file) throws FileNotFoundException {
        super("Time Data");

        timeController = new TimeController(file);
        String name = timeController.getName();

        Set<String> chatters = timeController.getChatters();
        selectedChatters =  chatters;

        JPanel chekBoxesPanel =  new JPanel();
        JPanel chartPanel =  new JPanel();
        JPanel chart2Panel =  new JPanel();

        for (String chatter : chatters) {
            JCheckBox checkBox = new JCheckBox(chatter, true);
            checkBox.addActionListener(e -> {
                JCheckBox cb = (JCheckBox)e.getSource();
                if (cb.isSelected()) {
                    selectedChatters.add(chatter);
                }
                else {
                    selectedChatters.remove(chatter);
                }

                chartPanel.removeAll();

                addBarGraphFromMap(timeController.getTimeMessages(selectedChatters),
                        "Messages by Hour", name, "Hours", 2, chartPanel);

                chart2Panel.removeAll();

                addHeatmap(chart2Panel);
            });

            chekBoxesPanel.add(checkBox);
        }

        addBarGraphFromMap(timeController.getTimeMessages(chatters),
                "Messages by Hour", name, "Weekday", 2, chartPanel);

        add(chekBoxesPanel);
        add(chartPanel);

        addHeatmap(chart2Panel);
        add(chart2Panel);
    }

    private void addHeatmap(JPanel panel) {
        DefaultXYZDataset dataset = timeController.getTimeWeekdayMessages(selectedChatters);

        XYBlockRenderer renderer = new XYBlockRenderer();
        renderer.setBlockWidth(1.0);
        renderer.setBlockHeight(1.0);

        double max = 1;

        for (int series = 0; series < dataset.getSeriesCount(); series++) {
            for (int item = 0; item < dataset.getItemCount(series); item++) {
                Number z = dataset.getZ(series, item);
                if (z != null) {
                    max = Math.max(max, z.doubleValue());
                }
            }
        }

        PaintScale paintScale = new GrayPaintScale(0, max);
        renderer.setPaintScale(paintScale);

        NumberAxis xAxis = new NumberAxis("Hour");
        xAxis.setRange(-0.5, 23.5);
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        NumberAxis yAxis = new NumberAxis("Weekday");
        yAxis.setRange(-0.5, 6.5);
        yAxis.setTickUnit(new NumberTickUnit(1));
        yAxis.setInverted(true);

        yAxis.setNumberFormatOverride(new NumberFormat() {
            @Override
            public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
                // Monday = 0 → DayOfWeek.of(1)
                return toAppendTo.append(DayOfWeek.of((int) number + 1).toString());
            }
            @Override public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
                return format((double) number, toAppendTo, pos);
            }
            @Override public Number parse(String source, ParsePosition parsePosition) {
                return null;
            }
        });

        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
        plot.setBackgroundPaint(Color.WHITE);

        JFreeChart chart = new JFreeChart(
                "Messages by Hour & Weekday",
                JFreeChart.DEFAULT_TITLE_FONT,
                plot,
                false
        );
        chart.setBackgroundPaint(Color.WHITE);

        PaintScaleLegend legend = new PaintScaleLegend(
                paintScale,
                new NumberAxis("Messages")
        );

        legend.setPosition(RectangleEdge.BOTTOM);
        legend.setStripWidth(8);
        legend.setPadding(0,10,0,10);
        chart.addSubtitle(legend);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1000, 450));

        panel.add(chartPanel);

        panel.updateUI();
    }
}
