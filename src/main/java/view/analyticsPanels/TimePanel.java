package view.analyticsPanels;

import controller.analytics.TimeController;
import view.AnalyticsPanel;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

public class TimePanel extends AnalyticsPanel {

    TimeController timeController;
    Set<String> selectedChatters;

    public TimePanel(File file) throws FileNotFoundException {
        super("Time Data");

        timeController = new TimeController(file);
        String name = timeController.getName();

        Set<String> chatters = timeController.getChatters();
        selectedChatters =  chatters;

        JPanel chekBoxesPanel =  new JPanel();
        JPanel chartPanel =  new JPanel();

        for (String chatter : chatters) {
            JCheckBox checkBox = new JCheckBox(chatter, true);
            checkBox.addActionListener(e -> {
                JCheckBox cb = (JCheckBox)e.getSource();
                if (cb.isSelected()) {
                    selectedChatters.add(chatter);
                }
                else {
                    selectedChatters.remove(chatter);
                }

                chartPanel.removeAll();

                addBarGraphFromMap(timeController.getTimeMessages(selectedChatters),
                        "Messages by Hour", name, "Hours", 2, chartPanel);
            });

            chekBoxesPanel.add(checkBox);
        }

        addBarGraphFromMap(timeController.getTimeMessages(chatters),
                "Messages by Hour", name, "Weekday", 2, chartPanel);

        add(chekBoxesPanel);
        add(chartPanel);
    }
}
