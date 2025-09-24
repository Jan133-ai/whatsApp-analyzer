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

public class MainWindow implements ActionListener {

    private JFrame window;
    static JLabel l;
    static JButton buttonShow;
    static JPanel chartPanel;
    private String chosenFile;
    private ChatInfo currentChatInfo;

    
    public MainWindow() {
        window = new JFrame();
        window.setTitle("WhatsApp-Analizer");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(1200,500);
        window.setLocationRelativeTo(null);

        JPanel selFile = new JPanel();
        selFile.setLayout(new GridLayout(0, 2, 5, 5));

        JLabel l1 = new JLabel("Select the .txt-File");
        l1.setBounds(500, 50, 120, 80);

        selFile.add(l1);

        JButton buttonOpen = new JButton("Chose File");
        buttonOpen.addActionListener(this);

        selFile.add(buttonOpen);

        l = new JLabel("no file selected");

        selFile.add(l);

        buttonShow = new JButton("Show Analytics");
        buttonShow.addActionListener(this);
        buttonShow.setEnabled(false);

        selFile.add(buttonShow);

        chartPanel = new JPanel();
        chartPanel.setLayout(new GridLayout(0, 2, 5, 5));

        window.add(selFile, BorderLayout.NORTH);

        window.add(chartPanel, BorderLayout.CENTER);

    }

    public void show() {
        window.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String com = e.getActionCommand();

        if (com.equals("Chose File")) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "TXT Files", "txt");
            fileChooser.setFileFilter(filter);

            int r = fileChooser.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                l.setText(fileChooser.getSelectedFile().getAbsolutePath());
                chosenFile = fileChooser.getSelectedFile().getAbsolutePath();
                buttonShow.setEnabled(true);
            }
        }
        else if (com.equals("Show Analytics")) {
            chartPanel.removeAll();
            try {
                currentChatInfo = new ChatInfo(chosenFile);
                Map<String, Integer> messageResults = currentChatInfo.callMessages(false, "", "0");
                Map<String, Integer> wordsResults = currentChatInfo.callWords(false, "", "0");

                addBarGraphFromMap(messageResults, "Messages");
                addBarGraphFromMap(wordsResults, "Words");
            } catch (FileNotFoundException err) {
                System.out.println("File Not Found");
            }
            
        }
        
    }

    public void addBarGraphFromMap(Map<String, Integer> map, String title) {
        String name = currentChatInfo.getName();

        DefaultCategoryDataset wordsDataset = new DefaultCategoryDataset( );
        for (Map.Entry<String, Integer> me : map.entrySet()) {
            if (!me.getKey().equals("Total")) {
                wordsDataset.addValue(me.getValue(), me.getKey(), "");
            }
        }

        JFreeChart messagesBarGraph = ChartFactory.createBarChart(title + ": " + name,
                "Sender", title, wordsDataset);

        ChartPanel panel = new ChartPanel(messagesBarGraph);

        chartPanel.add(panel);
        chartPanel.validate();
    }
}
