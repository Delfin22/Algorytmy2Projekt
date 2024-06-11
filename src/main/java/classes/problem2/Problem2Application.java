package classes.problem2;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.HashMap;

public class Problem2Application extends JFrame {
    public static void main(String [] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGui();
            }
        });
    }

    private static void createGui(){
        Problem2Application frame = new Problem2Application(); //creating frame

        /*FIRST ROW*/

        //configuration of field text
        JTextArea melodyText = new JTextArea(9,60);
        JScrollPane melodyPane = new JScrollPane(melodyText);
        melodyText.setLineWrap(true);
        melodyText.getDocument().addDocumentListener(new DocumentListener() {

            //dynamic changing upper case letters and characters out of given alphabet
            private void assistText(){
                Runnable assist = new Runnable() {
                    int lastIndex = melodyText.getText().length() - 1;
                    char lastLetter = melodyText.getText().charAt(lastIndex);

                    @Override
                    public void run() {
                        if(lastLetter >= 'A' && lastLetter <= 'Z'){
                            melodyText.setText(melodyText.getText().toLowerCase());
                        }
                        else if(!(lastLetter >= 'a' && lastLetter <= 'z')) {
                            melodyText.setText(melodyText.getText().substring(0,lastIndex));
                        }
                    }
                };
                SwingUtilities.invokeLater(assist);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                assistText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        //buttons configuration
        JButton randomStringButton = new JButton();
        randomStringButton.setFont(new Font("Arial",Font.PLAIN,23));
        randomStringButton.setText("<html><center>Wygeneruj losowy ciąg<br>znaków długości 100<center></html>");

        JButton clearButton = new JButton();
        clearButton.setFont(new Font("Arial",Font.PLAIN,23));
        clearButton.setText("<html><center>Wyczyść tekst</center></html>");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                melodyText.setText("");
            }
        });

        //scrollbar configuration
        JScrollBar randomStringScrollBar = new JScrollBar();
        randomStringScrollBar.setValue(100);
        randomStringScrollBar.setPreferredSize(new Dimension(20,150));
        randomStringScrollBar.setMinimum(1);
        randomStringScrollBar.setMaximum(1000);
        randomStringScrollBar.setOrientation(JScrollBar.VERTICAL);
        randomStringScrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                randomStringButton.setText("<html><center>Wygeneruj losowy ciąg<br>znaków długości " + randomStringScrollBar.getValue() + "</center></html>");
            }
        });

        //action listener for button
        randomStringButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                melodyText.setText("");
                melodyText.insert(PatternTools.generateRandomString(randomStringScrollBar.getValue()),0);
            }
        });

        /*SECOND ROW*/

        //pattern change text fields configuration
        JTextArea badPatternTextArea = new JTextArea(2,60);
        badPatternTextArea.setLineWrap(true);
        JScrollPane badPatternPane = new JScrollPane(badPatternTextArea);

        JTextArea goodPatternTextArea = new JTextArea(2,60);
        goodPatternTextArea.setLineWrap(true);
        JScrollPane goodPatternPane = new JScrollPane(badPatternTextArea);

        JButton changePatternButton = new JButton();
        changePatternButton.setFont(new Font("Arial",Font.PLAIN,23));
        changePatternButton.setText("Napraw melodię!");
        changePatternButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String badMelody = melodyText.getText();
                String badPattern = badPatternTextArea.getText();
                String goodPattern = goodPatternTextArea.getText();

                if(badMelody.isEmpty() || badPattern.isEmpty() || goodPattern.isEmpty()) {
                    JOptionPane.showMessageDialog(null,"Niewłasciwe nuty!","Ostrzeżenie",JOptionPane.WARNING_MESSAGE);
                }
                else if(badPattern.length() != goodPattern.length()){
                    JOptionPane.showMessageDialog(null,"Liczba dobrych i złych nut nie jest równa!","Ostrzeżenie",JOptionPane.WARNING_MESSAGE);
                }
                else{
                    String goodMelody = PatternTools.changePattern(badMelody, badPattern, goodPattern);
                    melodyText.setText(goodMelody);
                }
            }
        });

        /*THIRD ROW*/
        //encrypted text area
        JTextArea encryptedTextArea = new JTextArea(9,60);
        encryptedTextArea.setLineWrap(true);
        encryptedTextArea.setEditable(false);
        JScrollPane encryptedTextPane = new JScrollPane(encryptedTextArea);


        //declaration for fourth row - alphabet coding
        JTextArea codesArea = new JTextArea(9,60);
        codesArea.setLineWrap(true);
        codesArea.setEditable(false);



        //encrypt button
        JButton encryptButton = new JButton();
        encryptButton.setFont(new Font("Arial",Font.PLAIN,23));
        encryptButton.setText("<html><center>Zaszyfruj wiadomość na<br> maszynie informatyka </center></html>");
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String melody = melodyText.getText();
                Huffman huffman = new Huffman();
                String encryptedMelody = huffman.encodeText(melody);
                encryptedTextArea.setText(encryptedMelody);
                HashMap<Character, String> codes = huffman.getCodes();
                StringBuilder stringBuilder = new StringBuilder();
                int lineChecker = 0;
                for(char i = 'a'; i <= 'z'; i++){
                    if(codes.get(i) != null) {
                        stringBuilder.append(i).append(" : ").append(codes.get(i)).append("\t");
                        lineChecker++;
                    }
                    if(lineChecker%5 == 0)
                        stringBuilder.append("\n");
                }
                codesArea.setText(stringBuilder.toString());
            }
        });


        /* FRAME VISUAL CONFIGURATION ZONE*/

        //FIRST ROW//
        //text field visual configuration
        JPanel melodyPanel = new JPanel();
        melodyPanel.setLayout(new BorderLayout());
        TitledBorder melodyBorder = BorderFactory.createTitledBorder("WPISZ SWOJĄ MELODIĘ!");
        melodyBorder.setTitleFont(new Font("Arial",Font.PLAIN,17));
        melodyBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
        melodyBorder.setTitleJustification(TitledBorder.CENTER);
        melodyPane.setBorder(melodyBorder);
        melodyPanel.add(melodyPane,BorderLayout.CENTER);
        frame.add(melodyPanel);

        frame.add(randomStringScrollBar);

        //buttons configuration
        JPanel randomStringButtonPanel = new JPanel();
        randomStringButtonPanel.setLayout(new GridLayout(2,1));
        randomStringButtonPanel.add(randomStringButton);
        randomStringButtonPanel.add(clearButton);
        frame.add(randomStringButtonPanel);


        //SECOND ROW//
        //text fields for pattern changing configuration
        JPanel badPatternPanel = new JPanel();
        badPatternPanel.setLayout(new BorderLayout());
        TitledBorder badPatternBorder = BorderFactory.createTitledBorder("WPISZ ZŁE NUTY");
        badPatternBorder.setTitleFont(new Font("Arial",Font.PLAIN,14));
        badPatternBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
        badPatternBorder.setTitleJustification(TitledBorder.CENTER);
        badPatternTextArea.setBorder(badPatternBorder);
        badPatternPanel.add(badPatternTextArea,BorderLayout.CENTER);

        JPanel goodPatternPanel = new JPanel();
        goodPatternPanel.setLayout(new BorderLayout());
        TitledBorder goodPatternBorder = BorderFactory.createTitledBorder("WPISZ DOBRE NUTY");
        goodPatternBorder.setTitleFont(new Font("Arial",Font.PLAIN,14));
        goodPatternBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
        goodPatternBorder.setTitleJustification(TitledBorder.CENTER);
        goodPatternTextArea.setBorder(goodPatternBorder);
        goodPatternPanel.add(goodPatternTextArea,BorderLayout.CENTER);

        JPanel goodBadPatternPanel = new JPanel();
        goodBadPatternPanel.setLayout(new GridLayout(2,1));
        goodBadPatternPanel.add(badPatternPanel);
        goodBadPatternPanel.add(goodPatternPanel);

        frame.add(goodBadPatternPanel);

        //change button
        frame.add(changePatternButton);

        //empty panel
        JPanel emptyPanel = new JPanel();
        frame.add(emptyPanel);

        //THIRD ROW//
        frame.add(encryptedTextPane);
        frame.add(encryptButton);

        //FOURTH ROW//
        frame.add(codesArea);

        //global settings for frame
        frame.setLayout(new FlowLayout(FlowLayout.LEFT));
        frame.setSize(1024,720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
