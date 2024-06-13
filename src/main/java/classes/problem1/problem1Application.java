package classes.problem1;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.List;

public class problem1Application extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGui();
            }
        });
    }

    private static void createGui() {
        //main configuration
        problem1Application frame = new problem1Application();
        frame.setLayout(new GridBagLayout());


        //panel for printing points
        JPanel pointsPanel = new JPanel();
        pointsPanel.setPreferredSize(new Dimension(900,500));
        pointsPanel.setLayout(new GridLayout());

        PointComponent pointComponent = new PointComponent();
        pointComponent.setPoints(PointGenerator.createWorld(0,900,500));
        pointsPanel.add(pointComponent);


        //control panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        JPanel pointCoordinatesPanel = new JPanel();
        pointCoordinatesPanel.setPreferredSize(new Dimension(300,100));
        pointCoordinatesPanel.setLayout(new GridLayout(3,2));
        JLabel xCords = new JLabel();
        xCords.setText("Współrzędne x");
        JLabel yCords = new JLabel();
        yCords.setText("Współrzędne y");

        JTextArea xTextArea = new JTextArea(1,10);
        JTextArea yTextArea = new JTextArea(1,10);

        pointCoordinatesPanel.add(xCords);
        pointCoordinatesPanel.add(yCords);
        pointCoordinatesPanel.add(xTextArea);
        pointCoordinatesPanel.add(yTextArea);

        JButton addPointButton = new JButton();
        addPointButton.setText("<html><center>Dodaj punkt<br>do mapy</center></html>");
        addPointButton.setPreferredSize(new Dimension(250,50));
        addPointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    double x = Double.parseDouble(xTextArea.getText());
                    double y = Double.parseDouble(yTextArea.getText());
                    if(x > 900 || x < 0 || y > 500 || y < 0)
                        throw new IllegalArgumentException();
                    Point currPoint = new Point(x,y);
                    pointComponent.addPoint(currPoint);

                }catch (Exception exception){
                    JOptionPane.showMessageDialog(null,"Niewłasciwe współrzędne!","Ostrzeżenie",JOptionPane.WARNING_MESSAGE);
                }finally {
                    xTextArea.setText("");
                    yTextArea.setText("");
                }
            }
        });

        pointCoordinatesPanel.add(addPointButton);

        JButton removePointButton = new JButton();
        removePointButton.setText("<html><center>Usuń ostatnio<br>dodany punkt</center></html>");
        removePointButton.setPreferredSize(new Dimension(250,50));
        removePointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pointComponent.removeLastPoint();
            }
        });
        pointCoordinatesPanel.add(removePointButton);
        buttonsPanel.add(pointCoordinatesPanel);

        //panel for showing number of points selected for drawing
        JPanel numPointsPanel = new JPanel();
        numPointsPanel.setLayout(new BorderLayout());
        TitledBorder numPointsBorder = BorderFactory.createTitledBorder("ILOŚĆ PUNKTÓW");
        numPointsBorder.setTitleFont(new Font("Arial",Font.PLAIN,12));
        numPointsBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
        numPointsBorder.setTitleJustification(TitledBorder.CENTER);
        numPointsPanel.setBorder(numPointsBorder);

        JLabel numPointsLabel = new JLabel("20");
        numPointsLabel.setPreferredSize(new Dimension(120,50));
        numPointsLabel.setFont(new Font("Arial",Font.PLAIN,25));
        numPointsLabel.setHorizontalAlignment(JLabel.CENTER);
        numPointsLabel.setHorizontalAlignment(JLabel.CENTER);
        numPointsPanel.add(numPointsLabel);

        //scrollbar for selecting number of points
        JScrollBar pointsScrollBar = new JScrollBar();
        pointsScrollBar.setValue(20);
        pointsScrollBar.setPreferredSize(new Dimension(20,150));
        pointsScrollBar.setMinimum(3);
        pointsScrollBar.setMaximum(100);
        pointsScrollBar.setOrientation(JScrollBar.VERTICAL);
        pointsScrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                numPointsLabel.setText(String.valueOf(pointsScrollBar.getValue()));
            }
        });
        buttonsPanel.add(pointsScrollBar);
        buttonsPanel.add(numPointsPanel);

        //button for generating world
        JButton generateWorldButton = new JButton();
        generateWorldButton.setFont(new Font("Arial",Font.PLAIN,14));
        generateWorldButton.setText("<html><center>Stwórz świat<br>płaszczaków</center></html>");
        generateWorldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pointComponent.setPoints(PointGenerator.createWorld(pointsScrollBar.getValue(),900,500));
            }
        });

        JButton generateHullButton = new JButton();
        generateHullButton.setFont(new Font("Arial",Font.PLAIN,14));
        generateHullButton.setText("<html><center>Stwórz<br>ogrodzenie</center></html>");
        generateHullButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pointComponent.getPoints().size() <= 2) {
                    JOptionPane.showMessageDialog(null, "Wymagane są conajmniej 3 punkty!", "Ostrzeżenie", JOptionPane.WARNING_MESSAGE);
                } else {
                    pointComponent.setHull();
                }
            }
        });

        JButton clearWorldButton = new JButton();
        clearWorldButton.setText("<html><center>Usuń wszystkie<br>punkty</center></html>");
        clearWorldButton.setFont(new Font("Arial",Font.PLAIN,14));
        clearWorldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               pointComponent.clearPoints();
            }
        });

        JButton totalLengthButton = new JButton();
        totalLengthButton.setText("<html><center>Oblicz całkowitą<br>długość ogrodzenia</center></html>");
        totalLengthButton.setFont(new Font("Arial",Font.PLAIN,14));
        totalLengthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(pointComponent.getHull().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Najpierw należy stworzyć ogrodzenie!", "Ostrzeżenie", JOptionPane.WARNING_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null, PointsTools.calcWallLenght(pointComponent.getHull()), "Informacja", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


        buttonsPanel.add(generateWorldButton);
        buttonsPanel.add(generateHullButton);
        buttonsPanel.add(clearWorldButton);
        buttonsPanel.add(totalLengthButton);
        //settings of main panels
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.75;
        frame.add(pointsPanel,gbc);
        gbc.gridy = 1;
        gbc.weighty = 0;
        frame.add(buttonsPanel,gbc);
        //global frame settings
        frame.setSize(1024,720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}