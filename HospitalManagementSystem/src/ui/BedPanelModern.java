package ui;

import model.Bed;
import service.BedService;
import util.UITheme;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Modern Bed Allotment Panel
 * Keeps existing logic intact and improves UI.
 */
public class BedPanelModern extends JPanel {

    private final BedService service = new BedService();

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtPatientId;
    private JTextField txtDoctorId;
    private JTextField txtDate;
    private JTextField txtTime;

    public BedPanelModern() {
        setLayout(new BorderLayout(10, 10));
        setBackground(UITheme.FORM_BG);

        add(createHeader(), BorderLayout.NORTH);
        add(createTable(), BorderLayout.CENTER);
        add(createSideForm(), BorderLayout.EAST);

        loadBeds();
    }

    /* ================= HEADER ================= */

    private JComponent createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UITheme.HEADER_BG);
        panel.setPreferredSize(new Dimension(100, 50));

        JLabel title = new JLabel("Bed Allotment");
        title.setFont(UITheme.TITLE_FONT);
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        panel.add(title, BorderLayout.WEST);
        return panel;
    }

    /* ================= TABLE ================= */

    private JComponent createTable() {
        tableModel = new DefaultTableModel(
                new Object[]{"Bed ID", "Ward", "Bed No", "Status", "Patient ID"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setFont(UITheme.TEXT_FONT);
        table.getTableHeader().setFont(UITheme.LABEL_FONT);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Beds"));

        return scrollPane;
    }

    /* ================= SIDE FORM ================= */

    private JComponent createSideForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(320, 100));
        panel.setBackground(UITheme.PANEL_BG);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Allot / Release",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                UITheme.LABEL_FONT
        ));

        txtPatientId = createField();
        txtDoctorId = createField();
        txtDate = createField();
        txtDate.setText(LocalDate.now().toString());
        txtTime = createField();
        txtTime.setText("10:00");

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 10, 6, 10);
        gc.anchor = GridBagConstraints.WEST;
        gc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        addRow(panel, gc, row++, "Patient ID:", txtPatientId);
        addRow(panel, gc, row++, "Doctor ID:", txtDoctorId);
        addRow(panel, gc, row++, "Date:", txtDate);
        addRow(panel, gc, row++, "Time:", txtTime);

        JButton btnAllot = new JButton("Allot Bed");
        JButton btnRelease = new JButton("Release Bed");

        btnAllot.addActionListener(e -> allotBed());
        btnRelease.addActionListener(e -> releaseBed());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnPanel.add(btnAllot);
        btnPanel.add(btnRelease);

        gc.gridx = 0;
        gc.gridy = row;
        gc.gridwidth = 2;
        panel.add(btnPanel, gc);

        return panel;
    }

    /* ================= HELPER METHODS ================= */

    private JTextField createField() {
        JTextField field = new JTextField(12);
        field.setFont(UITheme.TEXT_FONT);
        return field;
    }

    private void addRow(JPanel panel, GridBagConstraints gc,
                        int y, String labelText, JComponent component) {
        gc.gridx = 0;
        gc.gridy = y;
        gc.weightx = 0;

        JLabel label = new JLabel(labelText);
        label.setFont(UITheme.LABEL_FONT);
        panel.add(label, gc);

        gc.gridx = 1;
        gc.weightx = 1;
        panel.add(component, gc);
    }

    /* ================= BUSINESS OPERATIONS ================= */

    private void loadBeds() {
        tableModel.setRowCount(0);
        List<Bed> beds = service.getAllBeds();

        for (Bed b : beds) {
            tableModel.addRow(new Object[]{
                    b.getBedId(),
                    b.getWard(),
                    b.getBedNumber(),
                    b.getStatus(),
                    b.getPatientId()
            });
        }
    }

    private void allotBed() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a bed from the table.",
                    "No Bed Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int bedId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            int patientId = Integer.parseInt(txtPatientId.getText().trim());
            int doctorId = Integer.parseInt(txtDoctorId.getText().trim());
            LocalDate date = LocalDate.parse(txtDate.getText().trim());
            String time = txtTime.getText().trim();

            service.allotBedAndCreateAppointment(
                    bedId, patientId, doctorId, date, time
            );

            JOptionPane.showMessageDialog(this, "Bed allotted successfully");
            loadBeds();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numeric IDs.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error allotting bed: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void releaseBed() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a bed to release.",
                    "No Bed Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int bedId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
        service.releaseBed(bedId);
        loadBeds();
        JOptionPane.showMessageDialog(this, "Bed released successfully");
    }
}

