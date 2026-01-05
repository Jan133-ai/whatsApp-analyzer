package view.analyticsPanels;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.flow.FlowPlot;
import org.jfree.data.flow.DefaultFlowDataset;
import view.AnalyticsPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TestPanel extends AnalyticsPanel {


    public TestPanel() {
        super("Test");

        DefaultFlowDataset<Integer> dataset = new DefaultFlowDataset<>();

        dataset.setFlow(0, 0, 0, 50.0);
        dataset.setFlow(0, 0, 1, 20.0);
        dataset.setFlow(0, 1, 0, 40.0);
        dataset.setFlow(0, 1, 1, 10.0);

        FlowPlot plot = new FlowPlot(dataset);

        plot.setNodeColorSwatch(new ArrayList<>(Arrays.asList(ChartColor.createDefaultColorArray())));

        JFreeChart chart = new JFreeChart("Sankey", JFreeChart.DEFAULT_TITLE_FONT,
                plot, true);

        ChartPanel chartPanel = new ChartPanel(chart);

        JPanel panel = new JPanel();
        panel.add(chartPanel);

        add(panel);
    }
}
