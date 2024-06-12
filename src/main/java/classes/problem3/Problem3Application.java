package classes.problem3;

import classes.problem1.*;
import classes.problem1.Point;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Problem3Application extends JFrame {
    public static Random rand = new Random();
    private List<Flatguy> flatheads = new ArrayList<>();
    private List<Landmark> hull;

    /*
    public static void main(String[] args) {
        List<Point> world = PointGenerator.createWorld(50, 800, 600);
        List<Flatguy> flatguys = Flatguy.generateLivingFlatguys(10);

        PointsTools tools = new PointsTools();
        List<Point> wall = PointsTools.findConvexHull(world);
        System.out.println("wall:");
        wall.forEach(System.out::println);

        List<Landmark> wallWithLights = addLightsToWall(wall);
        System.out.println("After lights were added");
        wallWithLights.forEach(System.out::println);

        WorkSchedule schedule = new WorkSchedule(flatguys, wallWithLights, WorkSchedule.WEEK);
        System.out.println("\n -- SCHEDULE --");
        for (WorkDay day : schedule.getSchedule()) {
            System.out.println(day);
        }
    }
    */

    public static List<Landmark> addLightsToWall(List<Point> wall) {
        List<Landmark> wallWithLights = new ArrayList<>();
        for (Point p : wall) {
            int brightness = rand.nextInt(Landmark.MIN_BRIGHTNESS, Landmark.MAX_BRIGHTNESS);
            wallWithLights.add(new Landmark(p, brightness));
        }

        return wallWithLights;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Problem3Application().createGui();
            }
        });
    }

    private static PointComponent accessPointComponent(JTabbedPane tabbedPane, int tab) {
        // Check if the index is valid
        if (tab >= 0 && tab < tabbedPane.getTabCount()) {
            Component tabComponent = tabbedPane.getComponentAt(tab);
            if (tabComponent instanceof JPanel tabPanel) {
                Component[] components = tabPanel.getComponents();
                for (Component component : components) {
                    // Check if the component is the one you want
                    if (component instanceof PointComponent) {
                        return (PointComponent) component;
                    }
                }
            } else if (tabComponent instanceof PointComponent) {
                return (PointComponent) tabComponent;
            }
        }
        return null;
    }

    private static LandmarkComponent accessLandmarkComponent(JTabbedPane tabbedPane, int tab) {
        // Check if the index is valid
        if (tab >= 0 && tab < tabbedPane.getTabCount()) {
            Component tabComponent = tabbedPane.getComponentAt(tab);
            if (tabComponent instanceof JPanel tabPanel) {
                Component[] components = tabPanel.getComponents();
                for (Component component : components) {
                    // Check if the component is the one you want
                    if (component instanceof LandmarkComponent) {
                        return (LandmarkComponent) component;
                    }
                }
            } else if (tabComponent instanceof LandmarkComponent) {
                return (LandmarkComponent) tabComponent;
            }
        }
        return null;
    }

    private void createGui() {
        //main configuration
        problem1Application frame = new problem1Application();
        frame.setLayout(new BorderLayout());


        //panel for printing points
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));

        int borderX = 640;
        int borderY = 480;
        // mainPanel.add(pointComponent, BorderLayout.CENTER);


        JList<String> livingFlatguys = new JList<>();
        mainPanel.add(new JScrollPane(livingFlatguys), BorderLayout.WEST);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.RIGHT);

        PointComponent hullPicture = new PointComponent(borderX, borderY);
        hullPicture.setPoints(PointGenerator.createWorld(0, borderX, borderY));
        tabbedPane.add("Hull", hullPicture);
        for (int i = 0; i < 7; ++i) {
            tabbedPane.add(String.format("Day %d", i + 1), new LandmarkComponent(borderX, borderY, hull));
        }

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(new JPanel(), BorderLayout.NORTH);  // for padding


        //control panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        JPanel pointCoordinatesPanel = new JPanel();
        pointCoordinatesPanel.setPreferredSize(new Dimension(300, 100));
        pointCoordinatesPanel.setLayout(new GridLayout(3, 2));
        JLabel xCords = new JLabel();
        xCords.setText("Współrzędne x");
        JLabel yCords = new JLabel();
        yCords.setText("Współrzędne y");

        JTextArea xTextArea = new JTextArea(1, 10);
        JTextArea yTextArea = new JTextArea(1, 10);

        pointCoordinatesPanel.add(xCords);
        pointCoordinatesPanel.add(yCords);
        pointCoordinatesPanel.add(xTextArea);
        pointCoordinatesPanel.add(yTextArea);

        JButton addPointButton = new JButton();
        addPointButton.setText("<html><center>Dodaj punkt<br>do mapy</center></html>");
        addPointButton.setPreferredSize(new Dimension(250, 50));
        addPointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double x = Double.parseDouble(xTextArea.getText());
                    double y = Double.parseDouble(yTextArea.getText());
                    if (x > borderX || x < 0 || y > borderY || y < 0)
                        throw new IllegalArgumentException();
                    Point currPoint = new Point(x, y);

                    accessPointComponent(tabbedPane, 0).addPoint(currPoint);
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Niewłasciwe współrzędne!", "Ostrzeżenie", JOptionPane.WARNING_MESSAGE);
                } finally {
                    xTextArea.setText("");
                    yTextArea.setText("");
                }
            }
        });

        pointCoordinatesPanel.add(addPointButton);

        JButton removePointButton = new JButton();
        removePointButton.setText("<html><center>Usuń ostatnio<br>dodany punkt</center></html>");
        removePointButton.setPreferredSize(new Dimension(250, 50));
        removePointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accessPointComponent(tabbedPane, 0).removeLastPoint();
            }
        });
        pointCoordinatesPanel.add(removePointButton);
        buttonsPanel.add(pointCoordinatesPanel);

        //panel for showing number of points selected for drawing
        JPanel numPointsPanel = new JPanel();
        numPointsPanel.setLayout(new BorderLayout());
        TitledBorder numPointsBorder = BorderFactory.createTitledBorder("ILOŚĆ PUNKTÓW");
        numPointsBorder.setTitleFont(new Font("Arial", Font.PLAIN, 17));
        numPointsBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
        numPointsBorder.setTitleJustification(TitledBorder.CENTER);
        numPointsPanel.setBorder(numPointsBorder);

        JLabel numPointsLabel = new JLabel("20");
        numPointsLabel.setPreferredSize(new Dimension(160, 50));
        numPointsLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        numPointsLabel.setHorizontalAlignment(JLabel.CENTER);
        numPointsLabel.setHorizontalAlignment(JLabel.CENTER);
        numPointsPanel.add(numPointsLabel);

        //scrollbar for selecting number of points
        JScrollBar pointsScrollBar = new JScrollBar();
        pointsScrollBar.setValue(20);
        pointsScrollBar.setPreferredSize(new Dimension(20, 150));
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
        generateWorldButton.setFont(new Font("Arial", Font.PLAIN, 20));
        generateWorldButton.setText("<html><center>Stwórz świat<br>płaszczaków</center></html>");
        generateWorldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PointComponent pointComponent = accessPointComponent(tabbedPane, 0);
                pointComponent.setPoints(PointGenerator.createWorld(pointsScrollBar.getValue(), borderX, borderY));

                // TODO: get from some scrollbar info about current number of flatheads
                flatheads = Flatguy.generateLivingFlatguys(10);
                String[] strRepresentation = flatheads.stream().map(Flatguy::toString).toList().toArray(new String[flatheads.size()]);
                livingFlatguys.setListData(strRepresentation);
            }
        });

        JButton generateHullButton = new JButton();
        generateHullButton.setFont(new Font("Arial", Font.PLAIN, 20));
        generateHullButton.setText("<html><center>Stwórz<br>ogrodzenie</center></html>");
        generateHullButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PointComponent pointComponent = accessPointComponent(tabbedPane, 0);
                if (pointComponent.getPoints().size() <= 2) {
                    JOptionPane.showMessageDialog(null, "Wymagane są conajmniej 3 punkty!", "Ostrzeżenie", JOptionPane.WARNING_MESSAGE);
                } else {
                    pointComponent.setHull();
                }
            }
        });

        JButton clearWorldButton = new JButton();
        clearWorldButton.setText("<html><center>Usuń wszystkie<br>punkty</center></html>");
        clearWorldButton.setFont(new Font("Arial", Font.PLAIN, 20));
        clearWorldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: this should be clear all life -> so we should clear all flatheads too
                PointComponent pointComponent = accessPointComponent(tabbedPane, 0);
                pointComponent.clearPoints();
            }
        });


        JButton addLights = new JButton();
        addLights.setText("<html><center>Dodaj swiatla</center></html>");
        addLights.setFont(new Font("Arial", Font.PLAIN, 20));
        addLights.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PointComponent pointComponent = accessPointComponent(tabbedPane, 0);
                hull = pointComponent.addLights();
                for (int i = 1; i < tabbedPane.getTabCount(); ++i) {
                    accessLandmarkComponent(tabbedPane, i).setHull(hull);
                }
                pointComponent.repaint();
            }
        });

        JButton prepareScheduleButton = new JButton();
        prepareScheduleButton.setText("<html><center>Rozklad</center></html>");
        prepareScheduleButton.setFont(new Font("Arial", Font.PLAIN, 20));

        prepareScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // add to tabs, scheduled
                // modify tab title with id of flathead
                // additionally mark stops as red
                // set landmarkcomponent to i-th position

                WorkSchedule workschedule = new WorkSchedule(flatheads, hull, tabbedPane.getTabCount() - 1);  // -1 because main hull tab
                WorkDay[] schedule = workschedule.getSchedule();
                System.out.println("\n -- SCHEDULE --");

                for (int i = 0; i < schedule.length; ++i) {
                    tabbedPane.setTitleAt(i + 1, String.format("Day %d: %s", i + 1, schedule[i].guard.toString()));
                    LandmarkComponent landmarkComponent = accessLandmarkComponent(tabbedPane, i + 1);
                    landmarkComponent.setHull(hull);
                    landmarkComponent.setWorkDay(schedule[i]);
                }
                /*
                for (int i = 0; i < schedule.length; ++i) {
                    LandmarkComponent landmarkComponent = accessLandmarkComponent(tabbedPane, i + 1);

                    System.out.println("prepareSchedule");
                }
                 */
            }
        });

        buttonsPanel.add(generateWorldButton);
        buttonsPanel.add(generateHullButton);
        buttonsPanel.add(clearWorldButton);
        buttonsPanel.add(addLights);
        buttonsPanel.add(prepareScheduleButton);
        //settings of main panels
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(buttonsPanel, BorderLayout.SOUTH);
        //global frame settings
        frame.setSize(1024, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
