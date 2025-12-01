package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Main application frame.
 * Shows different modules using CardLayout and provides
 * a menu bar, toolbar, and status bar.
 */
public class MainFrame extends JFrame {

    private final JPanel cardPanel = new JPanel(new CardLayout());
    private final JLabel statusLabel = new JLabel("Ready");
    private final JProgressBar progressBar = new JProgressBar();

    // Keep references to panels so toolbar actions can call them
    private final PatientPanelModern patientPanel   = new PatientPanelModern();
    private final AppointmentPanelModern appointmentPanel = new AppointmentPanelModern();
    private final BedPanelModern bedPanel = new BedPanelModern();
    private final DoctorPanel doctorPanel = new DoctorPanel();


    private String currentCard = "PATIENTS";

    public MainFrame() {
        super("Hospital Management System");
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setJMenuBar(createMenuBar());
        add(createToolBar(), BorderLayout.NORTH);

        // cards
        cardPanel.add(patientPanel, "PATIENTS");
cardPanel.add(appointmentPanel, "APPOINTMENTS");
cardPanel.add(bedPanel, "BEDS");
cardPanel.add(doctorPanel, "DOCTORS");

        add(cardPanel, BorderLayout.CENTER);
        add(createStatusBar(), BorderLayout.SOUTH);

        setSize(1200, 700);
        setLocationRelativeTo(null);
    }

    /* ================== MENUS & TOOLBAR ================== */

    private JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem miNew = new JMenuItem(new NewRecordAction());
        JMenuItem miSave = new JMenuItem(new SaveAction());
        JMenuItem miExit = new JMenuItem("Exit");
        miExit.setMnemonic(KeyEvent.VK_X);
        miExit.addActionListener(e -> dispose());

        fileMenu.add(miNew);
        fileMenu.add(miSave);
        fileMenu.addSeparator();
        fileMenu.add(miExit);

        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic(KeyEvent.VK_V);
        viewMenu.add(new JMenuItem(new SwitchCardAction("Patients", "PATIENTS")));
        viewMenu.add(new JMenuItem(new SwitchCardAction("Appointments", "APPOINTMENTS")));
        viewMenu.add(new JMenuItem(new SwitchCardAction("Bed Allotment", "BEDS")));
        viewMenu.add(new JMenuItem(new SwitchCardAction("Doctor Notes", "DOCTORS")));

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        JMenuItem miAbout = new JMenuItem("About");
        miAbout.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Hospital Management System\nBuilt in Java Swing.",
                        "About", JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(miAbout);

        bar.add(fileMenu);
        bar.add(viewMenu);
        bar.add(helpMenu);
        return bar;
    }

    private JToolBar createToolBar() {
        JToolBar tb = new JToolBar();
        tb.setFloatable(false);

        tb.add(new JButton(new SwitchCardAction("Patients", "PATIENTS")));
        tb.add(new JButton(new SwitchCardAction("Appointments", "APPOINTMENTS")));
        tb.add(new JButton(new SwitchCardAction("Beds", "BEDS")));
        tb.add(new JButton(new SwitchCardAction("Doctors", "DOCTORS")));

        tb.addSeparator();
        tb.add(new JButton(new NewRecordAction()));
        tb.add(new JButton(new SaveAction()));

        return tb;
    }

    private JPanel createStatusBar() {
        JPanel panel = new JPanel(new BorderLayout());
        statusLabel.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
        progressBar.setVisible(false);
        progressBar.setIndeterminate(false);
        panel.add(statusLabel, BorderLayout.WEST);
        panel.add(progressBar, BorderLayout.EAST);
        return panel;
    }

    /* ================== PUBLIC HELPERS ================== */

    public void showCard(String name) {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, name);
        currentCard = name;
    }

    public void setStatus(String text) {
        statusLabel.setText(text);
    }

    public void showProgress(boolean visible) {
        progressBar.setVisible(visible);
        progressBar.setIndeterminate(visible);
    }

    /* ================== ACTION CLASSES ================== */

    // Switch between cards
    private class SwitchCardAction extends AbstractAction {
        private final String cardName;

        public SwitchCardAction(String label, String cardName) {
            super(label);
            this.cardName = cardName;
            putValue(SHORT_DESCRIPTION, "Show " + label + " view");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            showCard(cardName);
            setStatus("Showing " + getValue(NAME));
        }
    }

    // New record action (currently implemented for Patients panel,
    // others still use their own "New" buttons)
    private class NewRecordAction extends AbstractAction {
        public NewRecordAction() {
            super("New");
            putValue(SHORT_DESCRIPTION, "Create new record (Ctrl+N)");
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
            putValue(MNEMONIC_KEY, (int) 'N');
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if ("PATIENTS".equals(currentCard)) {
                patientPanel.newRecordFromToolbar();
                setStatus("New patient record");
            } else {
                // For other screens, use their own New buttons
                setStatus("Use screen buttons for New in this view");
            }
        }
    }

    // Save action (implemented for Patients panel; other screens still use their own Save buttons)
    private class SaveAction extends AbstractAction {
        public SaveAction() {
            super("Save");
            putValue(SHORT_DESCRIPTION, "Save current data (Ctrl+S)");
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
            putValue(MNEMONIC_KEY, (int) 'S');
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if ("PATIENTS".equals(currentCard)) {
                setStatus("Saving patient...");
                patientPanel.saveFromToolbar();
                setStatus("Patient saved");
            } else {
                setStatus("Use screen buttons for Save in this view");
            }
        }
    }
}
