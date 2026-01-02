package view;

import view.analyticsPanels.OverallPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

public class MainPanel extends JPanel {

    public MainPanel(File file) throws FileNotFoundException {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        OverallPanel overallPanel = new OverallPanel(file);
        add(overallPanel, BorderLayout.CENTER);
    }
}
