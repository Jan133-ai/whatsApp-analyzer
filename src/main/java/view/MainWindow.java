package view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class MainWindow extends JFrame implements ActionListener {

    final private FileSelectorPanel fileSelectorPanel;
    private JScrollPane analyticsScrollPane;
    static JButton buttonShow;

    
    public MainWindow() {
        setTitle("WhatsApp-Analyzer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200,600);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("File Selection");
        Font labelFont = titleLabel.getFont();
        titleLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        fileSelectorPanel = new FileSelectorPanel();
        fileSelectorPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonShow = new JButton("Show Analytics");
        buttonShow.addActionListener(this);
        buttonShow.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(fileSelectorPanel);
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
                MainPanel mainPanel = new MainPanel(fileSelectorPanel.getSelectedFile());
                analyticsScrollPane = new JScrollPane(mainPanel);
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
