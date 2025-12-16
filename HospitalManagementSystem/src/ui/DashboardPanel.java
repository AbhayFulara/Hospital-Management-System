package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Dashboard panel showing main modules with icons.
 */
public class DashboardPanel extends JPanel {

    private final MainFrame mainFrame;

    public DashboardPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(30, 30, 30, 30));
        setBackground(Color.WHITE);

        add(createHeader(), BorderLayout.NORTH);
        add(createCards(), BorderLayout.CENTER);
    }

    // ================= HEADER =================
    private JComponent createHeader() {
        JLabel title = new JLabel("Hospital Management System", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(40, 40, 40));
        return title;
    }

    // ================= CARDS =================
    private JComponent createCards() {
        JPanel grid = new JPanel(new GridLayout(2, 2, 25, 25));
        grid.setOpaque(false);

        grid.add(createCard(
                "Patients",
                "/resources/icons/patients.png.jpeg",
                "PATIENTS"
        ));

        grid.add(createCard(
                "Doctors",
                "/resources/icons/Doctors.png.jpeg",
                "DOCTORS"
        ));

        grid.add(createCard(
                "Beds",
                "/resources/icons/Bed.png.jpeg",
                "BEDS"
        ));

        grid.add(createCard(
                "Appointments",
                "/resources/icons/Appointment.png.jpeg",
                "APPOINTMENTS"
        ));

        return grid;
    }

    // ================= SINGLE CARD =================
    private JPanel createCard(String title, String iconPath, String cardName) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel iconLabel = new JLabel(loadIcon(iconPath), JLabel.CENTER);

        JLabel textLabel = new JLabel(title, JLabel.CENTER);
        textLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        card.add(iconLabel, BorderLayout.CENTER);
        card.add(textLabel, BorderLayout.SOUTH);

        // Hover + click
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(245, 245, 245));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.WHITE);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showCard(cardName);
            }
        });

        return card;
    }

    // ================= ICON LOADER =================
    private ImageIcon loadIcon(String path) {
        try {
            ImageIcon icon = new ImageIcon(
                    getClass().getResource(path)
            );
            Image img = icon.getImage().getScaledInstance(
                    90, 90, Image.SCALE_SMOOTH
            );
            return new ImageIcon(img);
        } catch (Exception e) {
            System.out.println("Icon not found: " + path);
            return new ImageIcon();
        }
    }
}