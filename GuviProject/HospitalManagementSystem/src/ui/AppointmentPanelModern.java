package ui;

import model.Appointment;
import service.AppointmentService;
import util.UITheme;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Modern appointment management panel.
 */
public class AppointmentPanelModern extends JPanel {

    private final AppointmentService service = new AppointmentService();

    private JTextField txtPatientId, txtDoctorId, txtDate, txtTime;
    private JTable table;
    private DefaultTableModel model;

    public AppointmentPanelModern() {
        setLayout(new BorderLayout(10,10));
        setBackground(UITheme.FORM_BG);

        add(createHeader(), BorderLayout.NORTH);
        add(createForm(), BorderLayout.CENTER);
        add(createTable(), BorderLayout.SOUTH);

        loadToday();
    }

    /* ================= HEADER ================= */

    private JComponent createHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(UITheme.HEADER_BG);
        p.setPreferredSize(new Dimension(100,50));

        JLabel l = new JLabel("Appointment Management");
        l.setFont(UITheme.TITLE_FONT);
        l.setForeground(Color.WHITE);
        l.setBorder(BorderFactory.createEmptyBorder(10,20,10,10));

        p.add(l, BorderLayout.WEST);
        return p;
    }

    /* ================= FORM ================= */

    private JComponent createForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UITheme.PANEL_BG);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Appointment Details",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                UITheme.LABEL_FONT));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,12,6,12);
        gc.anchor = GridBagConstraints.WEST;
        gc.fill = GridBagConstraints.HORIZONTAL;

        txtPatientId = field();
        txtDoctorId  = field();
        txtDate      = field(LocalDate.now().toString());
        txtTime      = field("10:00");

        int r = 0;
        addRow(panel, gc, r++, "Patient ID:", txtPatientId);
        addRow(panel, gc, r++, "Doctor ID:", txtDoctorId);
        addRow(panel, gc, r++, "Date (yyyy-mm-dd):", txtDate);
        addRow(panel, gc, r++, "Time:", txtTime);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnNew = new JButton("New");
        JButton btnSave = new JButton("Create");
        JButton btnRefresh = new JButton("Refresh Today");

        btnNew.addActionListener(e -> clearForm());
        btnSave.addActionListener(e -> createAppointment());
        btnRefresh.addActionListener(e -> loadToday());

        btns.add(btnNew);
        btns.add(btnSave);
        btns.add(btnRefresh);

        gc.gridx=0; gc.gridy=r; gc.gridwidth=2; gc.anchor=GridBagConstraints.EAST;
        panel.add(btns, gc);

        return panel;
    }

    /* ================= TABLE ================= */

    private JComponent createTable() {
        model = new DefaultTableModel(
                new Object[]{"ID","Patient","Doctor","Date","Time","Status"},0) {
            public boolean isCellEditable(int r,int c){return false;}
        };

        table = new JTable(model);
        table.setRowHeight(24);
        table.setFont(UITheme.TEXT_FONT);
        table.getTableHeader().setFont(UITheme.LABEL_FONT);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createTitledBorder("Appointments"));
        sp.setPreferredSize(new Dimension(100,230));

        return sp;
    }

    /* ================= HELPERS ================= */

    private JTextField field() {
        JTextField f = new JTextField(15);
        f.setFont(UITheme.TEXT_FONT);
        return f;
    }

    private JTextField field(String value) {
        JTextField f = field();
        f.setText(value);
        return f;
    }

    private void addRow(JPanel p, GridBagConstraints gc, int y,
                        String label, JComponent comp) {
        gc.gridx=0; gc.gridy=y; gc.weightx=0;
        JLabel l = new JLabel(label);
        l.setFont(UITheme.LABEL_FONT);
        p.add(l,gc);
        gc.gridx=1; gc.weightx=1;
        p.add(comp,gc);
    }

    private void clearForm() {
        txtPatientId.setText("");
        txtDoctorId.setText("");
        txtDate.setText(LocalDate.now().toString());
        txtTime.setText("");
    }

    private void createAppointment() {
        try {
            Appointment a = new Appointment();
            a.setPatientId(Integer.parseInt(txtPatientId.getText()));
            a.setDoctorId(Integer.parseInt(txtDoctorId.getText()));
            a.setDate(LocalDate.parse(txtDate.getText()));
            a.setTimeSlot(txtTime.getText());
            a.setStatus("SCHEDULED");

            service.create(a);
            loadToday();
            clearForm();
            JOptionPane.showMessageDialog(this,"Appointment created");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,"Invalid data","Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadToday() {
        model.setRowCount(0);
        List<Appointment> list = service.getByDate(LocalDate.now());
        for (Appointment a : list) {
            model.addRow(new Object[]{
                    a.getAppointmentId(),
                    a.getPatientId(),
                    a.getDoctorId(),
                    a.getDate(),
                    a.getTimeSlot(),
                    a.getStatus()
            });
        }
    }
}
