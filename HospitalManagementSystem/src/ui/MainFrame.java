package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Main application frame.
 * Uses CardLayout to switch between Dashboard and modules.
 */
public class MainFrame extends JFrame {

    private final JPanel cardPanel = new JPanel(new CardLayout());
    private final JLabel statusLabel = new JLabel("Ready");

    // ===== PANELS =====
    private final DashboardPanel dashboardPanel;
    private final PatientPanelModern patientPanel;
    private final AppointmentPanelModern appointmentPanel;
    private final BedPanelModern bedPanel;
    private final DoctorPanel doctorPanel;

    private String currentCard = "DASHBOARD";

    public MainFrame() {
        super("Hospital Management System");

        // Initialize panels
        patientPanel = new PatientPanelModern();
        appointmentPanel = new AppointmentPanelModern();
        bedPanel = new BedPanelModern();
        doctorPanel = new DoctorPanel();
        dashboardPanel = new DashboardPanel(this);

        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setJMenuBar(createMenuBar());
        add(createToolBar(), BorderLayout.NORTH);

        // ===== CARD LAYOUT =====
        cardPanel.add(dashboardPanel, "DASHBOARD");
        cardPanel.add(patientPanel, "PATIENTS");
        cardPanel.add(appointmentPanel, "APPOINTMENTS");
        cardPanel.add(bedPanel, "BEDS");
        cardPanel.add(doctorPanel, "DOCTORS");

        add(cardPanel, BorderLayout.CENTER);
        add(createStatusBar(), BorderLayout.SOUTH);

        showCard("DASHBOARD"); // ⭐ OPEN DASHBOARD FIRST

        setSize(1200, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /* ================== MENU BAR ================== */

    private JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem miDashboard = new JMenuItem("Dashboard");
        miDashboard.addActionListener(e -> showCard("DASHBOARD"));

        JMenuItem miExit = new JMenuItem("Exit");
        miExit.addActionListener(e -> dispose());

        fileMenu.add(miDashboard);
        fileMenu.addSeparator();
        fileMenu.add(miExit);

        JMenu viewMenu = new JMenu("View");
        viewMenu.add(new JMenuItem(new SwitchCardAction("Dashboard", "DASHBOARD")));
        viewMenu.add(new JMenuItem(new SwitchCardAction("Patients", "PATIENTS")));
        viewMenu.add(new JMenuItem(new SwitchCardAction("Doctors", "DOCTORS")));
        viewMenu.add(new JMenuItem(new SwitchCardAction("Beds", "BEDS")));
        viewMenu.add(new JMenuItem(new SwitchCardAction("Appointments", "APPOINTMENTS")));

        JMenu helpMenu = new JMenu("Help");
        JMenuItem miAbout = new JMenuItem("About");
        miAbout.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "Hospital Management System\nBuilt using Java Swing",
                        "About",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );
        helpMenu.add(miAbout);

        bar.add(fileMenu);
        bar.add(viewMenu);
        bar.add(helpMenu);
        return bar;
    }

    /* ================== TOOLBAR ================== */

    private JToolBar createToolBar() {
        JToolBar tb = new JToolBar();
        tb.setFloatable(false);

        tb.add(new JButton(new SwitchCardAction("Dashboard", "DASHBOARD")));
        tb.addSeparator();
        tb.add(new JButton(new SwitchCardAction("Patients", "PATIENTS")));
        tb.add(new JButton(new SwitchCardAction("Doctors", "DOCTORS")));
        tb.add(new JButton(new SwitchCardAction("Beds", "BEDS")));
        tb.add(new JButton(new SwitchCardAction("Appointments", "APPOINTMENTS")));

        return tb;
    }

    /* ================== STATUS BAR ================== */

    private JPanel createStatusBar() {
        JPanel panel = new JPanel(new BorderLayout());
        statusLabel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        panel.add(statusLabel, BorderLayout.WEST);
        return panel;
    }

    /* ================== PUBLIC METHODS ================== */

    // ⭐ CALLED FROM DASHBOARD CLICKS
    public void showCard(String name) {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, name);
        currentCard = name;
        statusLabel.setText("Showing " + name);
    }

    /* ================== ACTION CLASS ================== */

    private class SwitchCardAction extends AbstractAction {
        private final String cardName;

        public SwitchCardAction(String label, String cardName) {
            super(label);
            this.cardName = cardName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            showCard(cardName);
        }
    }
}