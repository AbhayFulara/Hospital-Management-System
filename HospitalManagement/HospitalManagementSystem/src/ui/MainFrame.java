package ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("Hospital Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Use Tabs for Navigation
        JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);

        tabs.addTab("Patients", new PatientPanel());
        tabs.addTab("Doctors", new DoctorPanel());
        tabs.addTab("Appointments", new AppointmentPanel());

        // Apply simple UI styling
        UIManager.put("TabbedPane.selected", Color.decode("#AEDFF7"));
        UIManager.put("TabbedPane.contentAreaColor", Color.white);

        add(tabs, BorderLayout.CENTER);
    }
}
