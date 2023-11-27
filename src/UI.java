import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class UI extends JFrame {
    private Operation operation;
    private JRadioButton compressRadio = new JRadioButton();
    private JRadioButton decompressRadio = new JRadioButton();
    private JButton openFileButton = new JButton("Open");
    private JButton okButton = new JButton("OK");
    private JLabel fileNameLabel = new JLabel("Select a file");
    private JTextField outputFileName = new JTextField("Enter file name");

    private File inputFile;

    public UI() {
        // Action Listeners.
        openFileButton.addActionListener(this::onOpenFileButtonClick);
        okButton.addActionListener(this::onOkButtonClick);

        compressRadio.addActionListener(this::onCompressDecompressRadioClick);
        decompressRadio.addActionListener(this::onCompressDecompressRadioClick);

        // Properties.
        compressRadio.setText("Compress");
        decompressRadio.setText("Decompress");

        compressRadio.setBounds(120, 30, 120, 50);
        decompressRadio.setBounds(250, 30, 80, 50);

        openFileButton.setBounds(30, 30, 120, 50);
        fileNameLabel.setBounds(150, 30, 220, 50);

        outputFileName.setBounds(120, 150, 120, 50);
        okButton.setBounds(180, 150, 120, 50);

        okButton.setEnabled(false);

        ButtonGroup chooseGroup = new ButtonGroup();
        chooseGroup.add(compressRadio);
        chooseGroup.add(decompressRadio);

        // Main Panel
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panel.setLayout(new GridLayout(3, 4));
        panel.add(openFileButton);
        panel.add(fileNameLabel);
        panel.add(compressRadio);
        panel.add(decompressRadio);
        panel.add(outputFileName);
        panel.add(okButton);

        // Frame setup.
        this.add(panel, BorderLayout.CENTER);
        this.setTitle("Hello World!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setBounds(100, 100, 700, 500);
        this.pack();
        this.setVisible(true);
    }

    private void onCompressDecompressRadioClick(ActionEvent event) {
        setOperation();
    }

    private void onOkButtonClick(ActionEvent event) {
        HuffmanCoder coder = new HuffmanCoder(inputFile, outputFileName.getText());
        if (operation == null || inputFile == null) {
            this.okButton.setEnabled(false);
        }

        try {
            switch (operation) {
                case COMPRESS -> coder.compress();
                case DECOMPRESS -> coder.decompress();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Could not open file.");
        }
    }

    private void onOpenFileButtonClick(ActionEvent event) {
        JFileChooser fileDialog = new JFileChooser(".");

        int option = fileDialog.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            inputFile = fileDialog.getSelectedFile();
            fileNameLabel.setText(inputFile.getName());
            okButton.setEnabled(true);
        }
    }

    private void setOperation() {
        if (compressRadio.isSelected()) {
            operation = Operation.COMPRESS;
            okButton.setText("Compress");
        } else if (decompressRadio.isSelected()) {
            operation = Operation.DECOMPRESS;
            okButton.setText("Decompress");
        }
    }
}