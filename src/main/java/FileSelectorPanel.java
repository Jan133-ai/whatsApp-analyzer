import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileSelectorPanel extends JPanel {

    final private JLabel statusLabel;
    private File selectedFile;

    public FileSelectorPanel() {

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JLabel l1 = new JLabel("Select the .txt-File");
        l1.setBounds(500, 50, 120, 80);

        add(l1);

        JButton buttonOpen = new JButton("Chose File");
        buttonOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "TXT Files", "txt");
                fileChooser.setFileFilter(filter);

                int r = fileChooser.showOpenDialog(null);

                if (r == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    statusLabel.setText(fileChooser.getSelectedFile().getName());
                }
            }
        });

        add(buttonOpen);

        statusLabel = new JLabel("no file selected");

        add(statusLabel);
    }

    public File getSelectedFile() {
        return selectedFile;
    }
}
