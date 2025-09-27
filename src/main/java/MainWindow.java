import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import java.util.Map;

public class MainWindow extends JFrame implements ActionListener {

    private FileSelectorPanel fileSelectorPanel;
    private JScrollPane analyticsScrollPane;
    static JButton buttonShow;

    
    public MainWindow() {
        setTitle("WhatsApp-Analizer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200,500);
        setLocationRelativeTo(null);

        fileSelectorPanel = new FileSelectorPanel();

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(fileSelectorPanel);

        buttonShow = new JButton("Show Analytics");
        buttonShow.addActionListener(this);
        topPanel.add(buttonShow);

        add(topPanel, BorderLayout.NORTH);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String com = e.getActionCommand();
        if (com.equals("Show Analytics")) {
            try {
                if (analyticsScrollPane != null) {
                    remove(analyticsScrollPane);
                }
                AnalyticsPanel analyticsPanel = new AnalyticsPanel(fileSelectorPanel.getSelectedFile());
                analyticsScrollPane = new JScrollPane(analyticsPanel);
                analyticsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                analyticsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                analyticsScrollPane.getVerticalScrollBar().setUnitIncrement(10);
                add(analyticsScrollPane, BorderLayout.CENTER);

                revalidate();
                repaint();
            } catch (FileNotFoundException err) {
                System.out.println("File Not Found");
            }
        }
    }
}
