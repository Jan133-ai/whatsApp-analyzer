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

    JPanel mainPanel;
    private FileSelectorPanel fileSelectorPanel;
    private AnalyticsPanel analyticsPanel;
    static JButton buttonShow;
    static JPanel chartPanel;
    private ChatInfo currentChatInfo;

    
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
                if (analyticsPanel != null) {
                    remove(analyticsPanel);
                }
                analyticsPanel = new AnalyticsPanel(fileSelectorPanel.getSelectedFile());
                add(analyticsPanel, BorderLayout.CENTER);
                revalidate();
                repaint();
            } catch (FileNotFoundException err) {
                System.out.println("File Not Found");
            }
        }
    }
}
