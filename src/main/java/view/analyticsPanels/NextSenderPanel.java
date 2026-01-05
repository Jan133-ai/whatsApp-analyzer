package view.analyticsPanels;

import controller.analytics.NextSenderController;
import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.flow.FlowPlot;
import org.jfree.data.flow.DefaultFlowDataset;
import view.AnalyticsPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class NextSenderPanel extends AnalyticsPanel {

    NextSenderController nextSenderController;

    public NextSenderPanel(File file) throws FileNotFoundException {
        super("Next Message Data");

        nextSenderController = new NextSenderController(file);

        Map<String, Map<String, Integer>> nextSendersMap = nextSenderController.getNextSendersMap();

        String[] chatterArray = nextSenderController.getChatterArray();

        DefaultFlowDataset<String> dataset = new DefaultFlowDataset<>();

        for (String firstSender : chatterArray) {
            if (nextSendersMap.containsKey(firstSender)) {
                Map<String, Integer> replierMap = nextSendersMap.get(firstSender);
                for (String secondSender : chatterArray) {
                    dataset.setFlow(0, firstSender, secondSender, replierMap.getOrDefault(secondSender, 0));
                }
            }
        }

        FlowPlot plot = new FlowPlot(dataset);

        plot.setNodeColorSwatch(new ArrayList<>(Arrays.asList(ChartColor.createDefaultColorArray())));

        JFreeChart chart = new JFreeChart("Next Sender by Sender of previous Message", JFreeChart.DEFAULT_TITLE_FONT,
                plot, true);
        chart.setBackgroundPaint(Color.white);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1000, 450));
        chartPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 450));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.add(chartPanel);

        add(panel);

        JPanel labelPanel = new JPanel();
        labelPanel.setMaximumSize(new Dimension(1000, 10));
        labelPanel.setBackground(Color.white);
        labelPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelPanel.setLayout(new BorderLayout());
        labelPanel.setBorder(BorderFactory.createEmptyBorder(0,5,5, 5));

        JLabel label1 = new JLabel("First Message");
        JLabel label2 = new JLabel("Next Message");
        labelPanel.add(label1, BorderLayout.WEST);
        labelPanel.add(label2, BorderLayout.EAST);

        add(labelPanel);
    }
}
