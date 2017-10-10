package pl.crypto;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import static pl.crypto.Logic.*;

public class OneTimePad {

    private JFrame fileChooser;
    private JTextField saltFieldBeforeEncrypted;
    private JTextField messageFieldBeforeEncrypted;
    private JButton encryptButton;
    private JPanel encryptPanel;
    private JPanel decryptPanel;
    private JTextField encryptedMessageFieldBeforeDecrypted;
    private JTextField saltFieldBeforeDecrypted;
    private JButton decryptButton;
    private JTextField messageFieldAfterDecrypted;
    private JTextField encryptedMessageFieldAfterEncrypted;
    private JLabel encryptedMessageLabel2;
    private JLabel saltLabel2;
    private JLabel saltLabel1;
    private JLabel messageLabel2;
    private JLabel messageLabel1;
    private JLabel encryptedMessageLabel1;
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JButton openButton;
    private JTextArea fileToEncryptValue;
    private JTextField saltFieldValue;
    private JTextField encryptedMessageFromFileField;
    private JButton openButton1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextArea textArea1;
    private JButton saveButton;
    private JButton encryptButton1;
    private JButton decryptButton1;


    public OneTimePad() {
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageFieldBeforeEncrypted.getText();
                String salt = saltFieldBeforeEncrypted.getText();
                String decryptedMessage = encryptMessage(message, salt.toCharArray());
                encryptedMessageFieldAfterEncrypted.setText(decryptedMessage);
                encryptedMessageFieldBeforeDecrypted.setText(decryptedMessage);
            }
        });

        messageFieldBeforeEncrypted.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }

            void update() {
                String message = messageFieldBeforeEncrypted.getText();
                int size = message.length();
                char[] saltValue = generateRandomString(size);
                saltFieldBeforeEncrypted.setText(null);
                saltFieldBeforeEncrypted.setText(String.valueOf(saltValue));
                saltFieldBeforeDecrypted.setText(String.valueOf(saltValue));

                encryptedMessageFieldAfterEncrypted.setText(null);
                encryptedMessageFieldBeforeDecrypted.setText(null);
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String encryptedMessage = encryptedMessageFieldBeforeDecrypted.getText();
                String salt = saltFieldBeforeDecrypted.getText();
                String message = decryptMessage(encryptedMessage, salt.toCharArray());
                messageFieldAfterDecrypted.setText(message);
            }
        });

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser();
                // Demonstrate "Open" dialog:
                int rVal = c.showOpenDialog(fileChooser);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    String filename = c.getSelectedFile().getName();

                    FileReader fileReader;
                    String fileContents = "";
                    try {
                        fileReader = new FileReader(filename);
                        int i;

                        while ((i = fileReader.read()) != -1) {
                            char ch = (char) i;
                            fileContents = fileContents + ch;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    fileToEncryptValue.setText(fileContents);
                }
            }
        });
        fileToEncryptValue.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }

            void update() {
                int textSize = fileToEncryptValue.getText().length();
                saltFieldValue.setText(String.valueOf(generateRandomString(textSize)));
                textField1.setText(saltFieldValue.getText());
            }
        });
        encryptButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = fileToEncryptValue.getText();
                String salt = saltFieldValue.getText();
                String decryptedMessage = encryptMessage(message, salt.toCharArray());
                encryptedMessageFromFileField.setText(decryptedMessage);
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filename = JOptionPane.showInputDialog("Set filename: ");
                File file = new File(filename);
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                    bw.write(encryptedMessageFromFileField.getText());
                    bw.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        openButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser();
                String fileContents = "";
                int rVal = c.showOpenDialog(fileChooser);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    String filename = c.getSelectedFile().getName();

                    FileReader fileReader;
                    try {
                        fileReader = new FileReader(filename);
                        int i;
                        while ((i = fileReader.read()) != -1) {
                            char ch = (char) i;
                            fileContents = fileContents + ch;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                textField2.setText(fileContents);
            }
        });
        decryptButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String encryptedMessage = textField2.getText();
                String salt = textField1.getText();
                String message = decryptMessage(encryptedMessage, salt.toCharArray());
                textArea1.setText(message);
            }
        });

    }


    public static void main(String[] args) {
        JFrame jframe = new JFrame("OneTimePad");
        JFrame fileChooser = new JFrame("File...");
        jframe.setContentPane(new OneTimePad().panel1);
        jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setVisible(true);
    }

}
