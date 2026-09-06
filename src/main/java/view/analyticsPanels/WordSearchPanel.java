package view.analyticsPanels;

import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.analytics.WordSearchController;
import view.AnalyticsPanel;

public class WordSearchPanel extends AnalyticsPanel{

    WordSearchController wordSearchController;
    
    public WordSearchPanel(WordSearchController wordSearchController) throws FileNotFoundException{
        super("Word Search");

        this.wordSearchController = wordSearchController;
        String name = wordSearchController.getName();

        JPanel searchPanel = new JPanel();
        JTextField searchText = new JTextField("", 15);
        JButton searchButton = new JButton("Search");

        JPanel chartPanel = new JPanel();

        searchButton.addActionListener(e -> {
            chartPanel.removeAll();

            addBarGraphFromMap(wordSearchController.searchWord(searchText.getText()), searchText.getText(), name, "Sender", 1, chartPanel);
        });

        searchPanel.add(searchText);
        searchPanel.add(searchButton);

        addBarGraphFromMap(wordSearchController.searchWord(name), name, name, "Sender", 1, chartPanel);
    
        add(searchPanel);
        add(chartPanel);
    }
}
