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
    static JLabel infoLabel;
    private String chosenFile;
    private ChatInfo currentChatInfo;

    
    public MainWindow() {
        window = new JFrame();
        window.setTitle("WhatsApp-Analizer");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(800,500);
        window.setLocationRelativeTo(null);

        JPanel selFile = new JPanel();

        JLabel l1 = new JLabel("Select the .txt-File");
        l1.setBounds(500, 50, 120, 80);

        selFile.add(l1);

        JButton buttonOpen = new JButton("Chose File");
        buttonOpen.addActionListener(this);

        selFile.add(buttonOpen);

        l = new JLabel("no file selected");

        selFile.add(l);

        JPanel showDiag = new JPanel();

        buttonShow = new JButton("Show Analytics");
        buttonShow.addActionListener(this);
        buttonShow.setEnabled(false);

        showDiag.add(buttonShow);

        JPanel diagrams = new JPanel();

        infoLabel = new JLabel("Label");

        diagrams.add(infoLabel);

        window.add(selFile, BorderLayout.NORTH);

        window.add(showDiag, BorderLayout.CENTER);

        window.add(diagrams, BorderLayout.SOUTH);

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
            try {
                currentChatInfo = new ChatInfo(chosenFile);
                showMessages(currentChatInfo);

            } catch (FileNotFoundException err) {
                System.out.println("File Not Found");
            }
            
        }
        
    }

    public void showMessages(ChatInfo currentChatInfo) {
        Map<String, Integer> messageResults = currentChatInfo.callMessages(false, "", "0");
        String messagesString = "";
        for (Map.Entry<String, Integer> me : messageResults.entrySet()) {
            if (me.getKey() != "Total") {
                messagesString += (me.getKey() + " : " + me.getValue());
            } 
            else {
                messagesString += ("\n " + me.getKey() + " : " + me.getValue());
            }
        }
        infoLabel.setText(messagesString);
    }
}
