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
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Problem3Application extends JFrame {
    public static Random rand = new Random();
    private List<Flatguy> flatheads = new ArrayList<>();
    private List<Landmark> hull;
    private WorkSchedule workschedule;

    private List<String> queueForDay;

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
        // TODO: filter flatheads
        JRadioButton filterAllFlatheadsButton = new JRadioButton("All flatheads");
        filterAllFlatheadsButton.addActionListener(l -> {
            // TODO: if there is no life disable all radio buttons
            String[] strRepresentation = flatheads.stream().map(Flatguy::toString).toList().toArray(new String[flatheads.size()]);
            livingFlatguys.setListData(strRepresentation);
            filterAllFlatheadsButton.setSelected(true);
        });
        JRadioButton filterWorkersButton = new JRadioButton("All workers");
        ButtonGroup flatheadsFilter = new ButtonGroup();  // group ensures that only one is selected
        flatheadsFilter.add(filterAllFlatheadsButton);
        flatheadsFilter.add(filterWorkersButton);
        filterAllFlatheadsButton.setSelected(true);

        JPanel topLeftPanel = new JPanel();
        topLeftPanel.add(filterAllFlatheadsButton);
        topLeftPanel.add(filterWorkersButton);

        filterAllFlatheadsButton.setEnabled(false);
        filterWorkersButton.setEnabled(false);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(new JScrollPane(livingFlatguys));
        leftPanel.add(topLeftPanel, BorderLayout.NORTH);

        mainPanel.add(leftPanel, BorderLayout.WEST);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.RIGHT);

        PointComponent hullPicture = new PointComponent(borderX, borderY);
        hullPicture.setPoints(PointGenerator.createWorld(0, borderX, borderY));
        tabbedPane.add("Hull", hullPicture);
        for (int i = 0; i < 7; ++i) {
            tabbedPane.add(String.format("Day %d", i + 1), new LandmarkComponent(borderX, borderY, hull));
            tabbedPane.setEnabledAt(i + 1, false);
        }

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(new JPanel(), BorderLayout.NORTH);  // for padding


        //control panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2, 3, 5, 5));

        JPanel pointCoordinatesPanel = new JPanel();
        // TODO: przy dodawaniu wlacz opcje jezeli mamy juz powyzej 3 punktow
        // TODO: tak samo przy usuwaniu
        pointCoordinatesPanel.setBorder(BorderFactory.createTitledBorder("Dodaj punkt"));
        pointCoordinatesPanel.setPreferredSize(new Dimension(300, 100));
        pointCoordinatesPanel.setLayout(new GridLayout(3, 2, 5, 5));
        JLabel xCords = new JLabel("Współrzędne x");
        JLabel yCords = new JLabel("Współrzędne y");
        JTextField inputXfield = new JTextField();
        JTextField inputYfield = new JTextField();

        pointCoordinatesPanel.add(xCords);
        pointCoordinatesPanel.add(inputXfield);
        pointCoordinatesPanel.add(yCords);
        pointCoordinatesPanel.add(inputYfield);

        JButton addPointButton = new JButton();
        addPointButton.setText("<html><center>Dodaj punkt<br>do mapy</center></html>");
        addPointButton.setPreferredSize(new Dimension(250, 50));
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

        //panel for showing number of points selected for drawing
        JLabel numPointsLabel = new JLabel("20");
        numPointsLabel.setFont(new Font("Arial", Font.PLAIN, 25));

        JLabel numOfLivingFlatheadsLabel = new JLabel("10");
        numOfLivingFlatheadsLabel.setFont(new Font("Arial", Font.PLAIN, 25));

        JLabel requiredEnergyLabel = new JLabel("1");
        requiredEnergyLabel.setFont(new Font("Arial", Font.PLAIN, 25));

        //scrollbar for selecting number of points
        // TODO: change number of points in one run when
        JScrollBar pointsScrollBar = new JScrollBar();
        pointsScrollBar.setValue(20);
        pointsScrollBar.setMinimumSize(new Dimension(50, 20));
        pointsScrollBar.setMinimum(3);
        pointsScrollBar.setMaximum(100 + pointsScrollBar.getVisibleAmount());
        pointsScrollBar.setOrientation(JScrollBar.HORIZONTAL);
        pointsScrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                numPointsLabel.setText(String.valueOf(pointsScrollBar.getValue()));
            }
        });

        JScrollBar populationOfFlatheadsBar = new JScrollBar();
        // TODO: change list when adding new flatheads
        populationOfFlatheadsBar.setValue(10);
        populationOfFlatheadsBar.setMinimumSize(new Dimension(50, 20));
        populationOfFlatheadsBar.setMinimum(3);
        populationOfFlatheadsBar.setMaximum(100 + populationOfFlatheadsBar.getVisibleAmount());
        populationOfFlatheadsBar.setOrientation(JScrollBar.HORIZONTAL);
        populationOfFlatheadsBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                numOfLivingFlatheadsLabel.setText(String.valueOf(populationOfFlatheadsBar.getValue()));
            }
        });
        JScrollBar requiredEnergyBar = new JScrollBar();
        requiredEnergyBar.setValue(1);
        requiredEnergyBar.setMinimumSize(new Dimension(50, 20));
        requiredEnergyBar.setMinimum(Flatguy.MIN_ENERGY);
        requiredEnergyBar.setMaximum(Flatguy.MAX_ENERGY + requiredEnergyBar.getVisibleAmount());  // TODO: check why maximum value is 10 below this
        requiredEnergyBar.setOrientation(JScrollBar.HORIZONTAL);
        requiredEnergyBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                requiredEnergyLabel.setText(String.valueOf(requiredEnergyBar.getValue()));

                if (filterWorkersButton.isSelected()) {
                    String[] strRepresentation = flatheads.stream()
                            .filter(flatguy -> flatguy.getEnergy() >= requiredEnergyBar.getValue())
                            .sorted((f1, f2) -> {
                                return Integer.compare(f2.getEnergy(), f1.getEnergy());
                            })
                            .map(Flatguy::toString).toList().toArray(new String[flatheads.size()]);
                    livingFlatguys.setListData(strRepresentation);
                }
            }
        });
        filterWorkersButton.addActionListener(l -> {
            String[] strRepresentation = flatheads.stream()
                    .filter(flatguy -> flatguy.getEnergy() >= requiredEnergyBar.getValue())
                    .sorted((f1, f2) -> {
                        return Integer.compare(f2.getEnergy(), f1.getEnergy());
                    })
                    .map(Flatguy::toString).toList().toArray(new String[flatheads.size()]);
            livingFlatguys.setListData(strRepresentation);
        });

        // liczba punktow
        // liczba plaszakow
        // wymagana energia do bycia pracownikiem
        JPanel settingsFrame = new JPanel();
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Ustawienia Swiata");
        settingsFrame.setBorder(titledBorder);
        settingsFrame.setLayout(new GridLayout(3, 3, 10, 5));
        settingsFrame.add(new JLabel("Liczba punktow"));
        settingsFrame.add(pointsScrollBar);
        settingsFrame.add(numPointsLabel);

        settingsFrame.add(new JLabel("liczba placzakow"));
        settingsFrame.add(populationOfFlatheadsBar);
        settingsFrame.add(numOfLivingFlatheadsLabel);

        settingsFrame.add(new JLabel("wymagana energia"));
        settingsFrame.add(requiredEnergyBar);
        settingsFrame.add(requiredEnergyLabel);

        //button for generating world
        JButton generateWorldButton = new JButton();
        generateWorldButton.setFont(new Font("Arial", Font.PLAIN, 20));
        generateWorldButton.setText("<html><center>Stwórz świat<br>płaszczaków</center></html>");

        JButton generateHullButton = new JButton();
        generateHullButton.setFont(new Font("Arial", Font.PLAIN, 20));
        generateHullButton.setText("<html><center>Stwórz<br>ogrodzenie</center></html>");
        JButton clearWorldButton = new JButton();
        clearWorldButton.setText("<html><center>Usuń wszystkie<br>punkty</center></html>");
        clearWorldButton.setFont(new Font("Arial", Font.PLAIN, 20));


        JButton addLights = new JButton();
        addLights.setText("<html><center>Dodaj swiatla</center></html>");
        addLights.setFont(new Font("Arial", Font.PLAIN, 20));
        JButton prepareScheduleButton = new JButton();
        prepareScheduleButton.setText("<html><center>Rozplanuj</center></html>");
        prepareScheduleButton.setFont(new Font("Arial", Font.PLAIN, 20));

        prepareScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // add to tabs, scheduled
                // modify tab title with id of flathead
                // additionally mark stops as red
                // set landmarkcomponent to i-th position

                workschedule = new WorkSchedule(flatheads, hull, tabbedPane.getTabCount() - 1, requiredEnergyBar.getValue());  // -1 because main hull tab
                WorkDay[] schedule = workschedule.getSchedule();

                // TODO: when no worker is available set title to no worker available
                // But show in current queue list of current workers that were available for that day
                for (int i = 0; i < schedule.length; ++i) {
                    tabbedPane.setEnabledAt(i + 1, true);
                    if (schedule[i].guard == null) {
                        tabbedPane.setTitleAt(i + 1, String.format("Day %d: %s", i + 1, "<NO GUARD AVAILABLE>"));
                        LandmarkComponent landmarkComponent = accessLandmarkComponent(tabbedPane, i + 1);
                        landmarkComponent.setHull(hull);
                        landmarkComponent.setWorkDay(schedule[i]);
                        continue;
                    }

                    tabbedPane.setTitleAt(i + 1, String.format("Day %d: %s", i + 1, schedule[i].guard.toString()));
                    LandmarkComponent landmarkComponent = accessLandmarkComponent(tabbedPane, i + 1);
                    landmarkComponent.setHull(hull);
                    landmarkComponent.setWorkDay(schedule[i]);
                }


                filterWorkersButton.setEnabled(true);
            }
        });

        generateWorldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PointComponent pointComponent = accessPointComponent(tabbedPane, 0);
                pointComponent.setPoints(PointGenerator.createWorld(pointsScrollBar.getValue(), borderX, borderY));

                // TODO: get from some scrollbar info about current number of flatheads
                flatheads = Flatguy.generateLivingFlatguys(populationOfFlatheadsBar.getValue());
                String[] strRepresentation = flatheads.stream().map(Flatguy::toString).toList().toArray(new String[flatheads.size()]);
                livingFlatguys.setListData(strRepresentation);

                filterAllFlatheadsButton.setSelected(true);
                filterAllFlatheadsButton.setEnabled(true);
                filterWorkersButton.setEnabled(false);

                generateHullButton.setEnabled(true);
                addLights.setEnabled(false);
                prepareScheduleButton.setEnabled(false);

                // remove tab names
                for (int i = 1; i < tabbedPane.getTabCount(); ++i) {
                    tabbedPane.setEnabledAt(i, false);
                    tabbedPane.setTitleAt(i, String.format("Day %d", i));
                    LandmarkComponent landmarkComponent = accessLandmarkComponent(tabbedPane, i);
                    landmarkComponent.setHull(hull);
                }
            }
        });

        clearWorldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: this should be clear all life -> so we should clear all flatheads too
                PointComponent pointComponent = accessPointComponent(tabbedPane, 0);
                pointComponent.clearPoints();
                // TODO: when no worker is available title of page should be <NO WORKER>
                // remove all pages from jtabbedpane
                // change titles of all panes

                // remove all points
                // remove all life

                flatheads.clear();
                livingFlatguys.setListData(new String[]{});
                for (int i = 1; i < tabbedPane.getTabCount(); ++i) {
                    tabbedPane.setTitleAt(i, String.format("Day %d", i));
                    tabbedPane.setEnabledAt(i, false);
                }
                tabbedPane.setSelectedIndex(0);

                // disable radio buttons
                filterAllFlatheadsButton.setEnabled(false);
                filterWorkersButton.setEnabled(false);

                addLights.setEnabled(false);
                prepareScheduleButton.setEnabled(false);
                generateHullButton.setEnabled(false);
            }
        });


        generateHullButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PointComponent pointComponent = accessPointComponent(tabbedPane, 0);
                if (pointComponent.getPoints().size() <= 2) {
                    JOptionPane.showMessageDialog(null, "Wymagane są conajmniej 3 punkty!", "Ostrzeżenie", JOptionPane.WARNING_MESSAGE);
                } else {
                    pointComponent.setHull();
                    addLights.setEnabled(true);
                }
            }
        });
        addLights.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PointComponent pointComponent = accessPointComponent(tabbedPane, 0);
                hull = pointComponent.addLights();
                for (int i = 1; i < tabbedPane.getTabCount(); ++i) {
                    accessLandmarkComponent(tabbedPane, i).setHull(hull);
                }
                pointComponent.repaint();
                prepareScheduleButton.setEnabled(true);
            }
        });


        addPointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double x = Double.parseDouble(inputXfield.getText());
                    double y = Double.parseDouble(inputYfield.getText());
                    if (x > borderX || x < 0 || y > borderY || y < 0)
                        throw new IllegalArgumentException();
                    Point currPoint = new Point(x, y);

                    accessPointComponent(tabbedPane, 0).addPoint(currPoint);
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Niewłasciwe współrzędne!", "Ostrzeżenie", JOptionPane.WARNING_MESSAGE);
                } finally {
                    inputXfield.setText("");
                    inputYfield.setText("");
                }
            }
        });


        generateHullButton.setEnabled(false);
        addLights.setEnabled(false);
        prepareScheduleButton.setEnabled(false);

        buttonsPanel.add(generateWorldButton);
        buttonsPanel.add(generateHullButton);
        buttonsPanel.add(clearWorldButton);
        buttonsPanel.add(addLights);
        buttonsPanel.add(prepareScheduleButton);
        buttonsPanel.setBorder(BorderFactory.createTitledBorder("Panel"));
        //settings of main panels


        JPanel wholeWindowPanel = new JPanel();
        wholeWindowPanel.setLayout(new BorderLayout());
        wholeWindowPanel.setBorder(new EmptyBorder(0, 5, 10, 5));
        wholeWindowPanel.add(mainPanel, BorderLayout.CENTER);
        // frame.add(mainPanel, BorderLayout.CENTER);
        wholeWindowPanel.add(mainPanel, BorderLayout.CENTER);


        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer, BoxLayout.X_AXIS));
        // footer.add(settingsFrame, BorderLayout.WEST);
        footer.add(settingsFrame);
        footer.add(Box.createHorizontalStrut(10));
        footer.add(pointCoordinatesPanel);
        footer.add(Box.createHorizontalStrut(10));
        footer.add(buttonsPanel);
        //frame.add(footer, BorderLayout.SOUTH);
        wholeWindowPanel.add(footer, BorderLayout.SOUTH);

        frame.add(wholeWindowPanel);

        // frame.add(buttonsPanel, BorderLayout.SOUTH);
        //global frame settings
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
