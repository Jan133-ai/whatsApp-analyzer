import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

public class AnalyticsPanel extends JPanel {

    JPanel chartPanel;
    ChatInfo currentChatInfo;

    public AnalyticsPanel(File file) throws FileNotFoundException {

        JRadioButton radioButtonBar = new JRadioButton("Bar Graph");
        radioButtonBar.setSelected(true);
        JRadioButton radioButtonPie = new JRadioButton("Round Graph");

        ButtonGroup choice = new ButtonGroup();
        choice.add(radioButtonBar);
        choice.add(radioButtonPie);

        add(radioButtonBar);
        add(radioButtonPie);

        radioButtonBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chartPanel.removeAll();

                Map<String, Integer> messageResults = currentChatInfo.callMessages(false, "", "0");
                Map<String, Integer> wordsResults = currentChatInfo.callWords(false, "", "0");

                addBarGraphFromMap(messageResults, "Messages", chartPanel);
                addBarGraphFromMap(wordsResults, "Words", chartPanel);
            }
        });


        radioButtonPie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chartPanel.removeAll();

                Map<String, Integer> messageResults = currentChatInfo.callMessages(false, "", "0");
                Map<String, Integer> wordsResults = currentChatInfo.callWords(false, "", "0");

                addPieChartFromMap(messageResults, "Messages", chartPanel);
                addPieChartFromMap(wordsResults, "Words", chartPanel);
            }
        });

        chartPanel = new JPanel();
        chartPanel.setLayout(new GridLayout(0, 2, 0, 5));


        currentChatInfo = new ChatInfo(file);
        Map<String, Integer> messageResults = currentChatInfo.callMessages(false, "", "0");
        Map<String, Integer> wordsResults = currentChatInfo.callWords(false, "", "0");

        addBarGraphFromMap(messageResults, "Messages", chartPanel);
        addBarGraphFromMap(wordsResults, "Words", chartPanel);

        JScrollPane scrollPane = new JScrollPane(chartPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void addBarGraphFromMap(Map<String, Integer> map, String title, JPanel panel) {
        String name = currentChatInfo.getName();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> me : map.entrySet()) {
            if (!me.getKey().equals("Total")) {
                dataset.addValue(me.getValue(), me.getKey(), title);
            }
        }

        JFreeChart barChart = ChartFactory.createBarChart(title + ": " + name,
                "Sender", title, dataset);

        ChartPanel chPanel = new ChartPanel(barChart);
        chPanel.setPreferredSize(new Dimension(600, 350));
        chPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 350));
        chPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(chPanel);
        panel.validate();
    }

    public void addPieChartFromMap(Map<String, Integer> map, String title, JPanel panel) {
        String name = currentChatInfo.getName();

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Integer> me : map.entrySet()) {
            if (!me.getKey().equals("Total")) {
                dataset.setValue(me.getKey(), me.getValue());
            }
        }

        JFreeChart pieChart = ChartFactory.createPieChart(title + ": " + name, dataset);

        ChartPanel chPanel = new ChartPanel(pieChart);
        chPanel.setPreferredSize(new Dimension(600, 350));
        chPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 350));
        chPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(chPanel);
        panel.validate();
    }
}
