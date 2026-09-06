package view;

import javax.swing.*;

import controller.MainController;

import java.io.File;
import java.io.FileNotFoundException;

public class MainPanel extends JPanel {

    MainController mainController;

    public MainPanel(File file) throws FileNotFoundException {

        mainController = new MainController(file);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for (JPanel panel : mainController.getPanelsList()) {
            add(panel);
        }
    }
}
