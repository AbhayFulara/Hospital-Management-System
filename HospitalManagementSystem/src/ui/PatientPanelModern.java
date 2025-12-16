package ui;

import model.Patient;
import service.PatientService;
import util.UITheme;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Modern patient panel: header + form + table.
 * Uses PatientService for all CRUD operations.
 */
public class PatientPanelModern extends JPanel {

    private final PatientService service = new PatientService();

    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtAge;
    private JTextField txtPhone;
    private JComboBox<String> cbGender;
    private JTextArea txtAddress;

    private JTable table;
    private DefaultTableModel tableModel;

    public PatientPanelModern() {
        setLayout(new BorderLayout(10, 10));
        setBackground(UITheme.FORM_BG);

        add(createHeader(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createTablePanel(), BorderLayout.SOUTH);

        loadPatients();
    }

    /* ================== PUBLIC METHODS USED BY TOOLBAR ================== */

    public void newRecordFromToolbar() {
        clearForm();
        table.clearSelection();
    }

    public void saveFromToolbar() {
        savePatient();
    }

    public void refreshFromToolbar() {
        loadPatients();
    }

    /* ================== UI CREATION ================== */

    private JComponent createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(UITheme.HEADER_BG);
        panel.setPreferredSize(new Dimension(100, 50));

        JLabel title = new JLabel("Patient Management");
        title.setFont(UITheme.TITLE_FONT);
        title.setForeground(UITheme.HEADER_FG);
        title.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        panel.add(title, BorderLayout.WEST);
        return panel;
    }

    private JComponent createCenterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(UITheme.PANEL_BG);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Patient Information",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                UITheme.LABEL_FONT
        ));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 12, 6, 12);
        gc.anchor = GridBagConstraints.WEST;
        gc.fill = GridBagConstraints.HORIZONTAL;

        txtId = createTextField(false);
        txtName = createTextField(true);
        txtAge = createTextField(true);
        txtPhone = createTextField(true);

        cbGender = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        cbGender.setFont(UITheme.TEXT_FONT);

        txtAddress = new JTextArea(3, 20);
        txtAddress.setFont(UITheme.TEXT_FONT);
        JScrollPane addressScroll = new JScrollPane(txtAddress);

        int row = 0;

        addRow(panel, gc, row++, "Patient ID:", txtId);
        addRow(panel, gc, row++, "Name:", txtName);
        addRow(panel, gc, row++, "Age:", txtAge);
        addRow(panel, gc, row++, "Gender:", cbGender);
        addRow(panel, gc, row++, "Phone:", txtPhone);
        addRow(panel, gc, row++, "Address:", addressScroll);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        JButton btnNew    = new JButton("New");
        JButton btnSave   = new JButton("Save");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear  = new JButton("Clear");

        btnNew.setToolTipText("Create new patient");
        btnSave.setToolTipText("Save patient");
        btnDelete.setToolTipText("Delete selected patient");
        btnClear.setToolTipText("Clear form");

        btnNew.addActionListener(e -> newRecordFromToolbar());
        btnSave.addActionListener(e -> savePatient());
        btnDelete.addActionListener(e -> deletePatient());
        btnClear.addActionListener(e -> clearForm());

        btnPanel.add(btnNew);
        btnPanel.add(btnSave);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);

        gc.gridx = 0;
        gc.gridy = row;
        gc.gridwidth = 2;
        gc.anchor = GridBagConstraints.EAST;
        panel.add(btnPanel, gc);

        return panel;
    }

    private JComponent createTablePanel() {
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Name", "Age", "Gender", "Phone"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(UITheme.TEXT_FONT);
        table.setRowHeight(24);
        table.getTableHeader().setFont(UITheme.LABEL_FONT);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.getSelectionModel().addListSelectionListener(e -> fillFormFromTable());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Patient Records"));
        scrollPane.setPreferredSize(new Dimension(100, 230));

        return scrollPane;
    }

    private JTextField createTextField(boolean editable) {
        JTextField tf = new JTextField(18);
        tf.setFont(UITheme.TEXT_FONT);
        tf.setEditable(editable);
        if (!editable) {
            tf.setBackground(new Color(230, 230, 230));
        }
        return tf;
    }

    private void addRow(JPanel panel, GridBagConstraints gc, int row,
                        String labelText, JComponent comp) {
        gc.gridx = 0;
        gc.gridy = row;
        gc.weightx = 0;
        JLabel label = new JLabel(labelText);
        label.setFont(UITheme.LABEL_FONT);
        panel.add(label, gc);

        gc.gridx = 1;
        gc.weightx = 1;
        panel.add(comp, gc);
    }

    /* ================== DATA OPERATIONS ================== */

    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtAge.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        cbGender.setSelectedIndex(0);
    }

    private Patient readForm() {
        Patient p = new Patient();
        if (!txtId.getText().trim().isEmpty()) {
            p.setPatientId(Integer.parseInt(txtId.getText().trim()));
        }
        p.setName(txtName.getText().trim());
        p.setAge(Integer.parseInt(txtAge.getText().trim()));
        p.setGender(cbGender.getSelectedItem().toString());
        p.setPhone(txtPhone.getText().trim());
        p.setAddress(txtAddress.getText().trim());
        return p;
    }

    private void savePatient() {
        try {
            if (txtName.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name is required",
                        "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (txtAge.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Age is required",
                        "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Patient p = readForm();
            service.save(p);
            loadPatients();
            clearForm();
            JOptionPane.showMessageDialog(this, "Patient saved successfully");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Age must be a number", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error saving patient: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletePatient() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Select a patient to delete", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int confirmed = JOptionPane.showConfirmDialog(
                this, "Delete selected patient?", "Confirm",
                JOptionPane.YES_NO_OPTION);
        if (confirmed == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtId.getText().trim());
            service.delete(id);
            loadPatients();
            clearForm();
        }
    }

    private void loadPatients() {
        List<Patient> list = service.findAll();
        tableModel.setRowCount(0);
        for (Patient p : list) {
            tableModel.addRow(new Object[]{
                    p.getPatientId(),
                    p.getName(),
                    p.getAge(),
                    p.getGender(),
                    p.getPhone()
            });
        }
    }

    private void fillFormFromTable() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        txtId.setText(String.valueOf(tableModel.getValueAt(row, 0)));
        txtName.setText(String.valueOf(tableModel.getValueAt(row, 1)));
        txtAge.setText(String.valueOf(tableModel.getValueAt(row, 2)));
        cbGender.setSelectedItem(tableModel.getValueAt(row, 3));
        txtPhone.setText(String.valueOf(tableModel.getValueAt(row, 4)));
        // Address is not shown in table; you can load it if needed using the ID
    }
}


