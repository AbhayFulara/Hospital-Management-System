package ui;

import model.Doctor;
import service.DoctorService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class DoctorPanel extends JPanel {

    private JTextField txtId, txtName, txtSpec, txtSearch;
    private JTextArea txtNotes;
    private JTable table;
    private DefaultTableModel tableModel;

    private DoctorService service = new DoctorService();

    public DoctorPanel() {
        setLayout(new BorderLayout(10, 10));
        add(createHeader(), BorderLayout.NORTH);
        add(createBody(), BorderLayout.CENTER);
        loadDoctors();
    }

    private JPanel createHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(140, 0, 0));
        p.setPreferredSize(new Dimension(100, 55));

        JLabel title = new JLabel("  Doctor Management");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        p.add(title, BorderLayout.WEST);
        return p;
    }

    private JPanel createBody() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.add(createFormPanel());
        panel.add(createListPanel());
        return panel;
    }

    private JPanel createFormPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("Doctor Details"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.anchor = GridBagConstraints.WEST;

        txtId = new JTextField(8); txtId.setEditable(false);
        txtName = new JTextField(15);
        txtSpec = new JTextField(15);
        txtNotes = new JTextArea(4,15);

        int r = 0;
        addRow(p,c,r++,"ID:",txtId);
        addRow(p,c,r++,"Name:",txtName);
        addRow(p,c,r++,"Specialization:",txtSpec);

        JButton btnSave = new JButton("Save / Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");

        JPanel bp = new JPanel();
        bp.add(btnSave); bp.add(btnDelete); bp.add(btnClear);

        c.gridx=0; c.gridy=r; c.gridwidth=2;
        p.add(bp,c);

        btnSave.addActionListener(e -> saveOrUpdate());
        btnDelete.addActionListener(e -> deleteDoctor());
        btnClear.addActionListener(e -> clearForm());

        return p;
    }

    private JPanel createListPanel() {
        JPanel p = new JPanel(new BorderLayout(5,5));
        p.setBorder(BorderFactory.createTitledBorder("Doctors"));

        txtSearch = new JTextField();
        txtSearch.setToolTipText("Search doctor by name");
        txtSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                filterDoctors(txtSearch.getText());
            }
        });

        tableModel = new DefaultTableModel(new Object[]{"ID","Name","Specialization"},0);
        table = new JTable(tableModel);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow()!=-1) {
                int r = table.getSelectedRow();
                txtId.setText(tableModel.getValueAt(r,0).toString());
                txtName.setText(tableModel.getValueAt(r,1).toString());
                txtSpec.setText(tableModel.getValueAt(r,2).toString());

                Doctor d = service.findAll().get(r);
                txtNotes.setText(d.getNotes());
            }
        });

        p.add(txtSearch, BorderLayout.NORTH);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        return p;
    }

    private void saveOrUpdate() {
        Doctor d = new Doctor();
        d.setName(txtName.getText());
        d.setSpecialization(txtSpec.getText());
        d.setNotes(txtNotes.getText());

        if (txtId.getText().isEmpty()) {
            service.saveDoctor(d);
            JOptionPane.showMessageDialog(this,"Doctor Added");
        } else {
            d.setDoctorId(Integer.parseInt(txtId.getText()));
            service.updateDoctor(d);
            JOptionPane.showMessageDialog(this,"Doctor Updated");
        }
        clearForm();
        loadDoctors();
    }

    private void deleteDoctor() {
        if (txtId.getText().isEmpty()) return;
        service.deleteDoctor(Integer.parseInt(txtId.getText()));
        clearForm();
        loadDoctors();
    }

    private void loadDoctors() {
        tableModel.setRowCount(0);
        for (Doctor d : service.findAll()) {
            tableModel.addRow(new Object[]{
                    d.getDoctorId(), d.getName(), d.getSpecialization()
            });
        }
    }

    private void filterDoctors(String key) {
        tableModel.setRowCount(0);
        for (Doctor d : service.findAll()) {
            if (d.getName().toLowerCase().contains(key.toLowerCase())) {
                tableModel.addRow(new Object[]{
                        d.getDoctorId(), d.getName(), d.getSpecialization()
                });
            }
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtSpec.setText("");
        txtNotes.setText("");
    }

    private void addRow(JPanel p, GridBagConstraints c, int y, String l, JComponent comp) {
        c.gridx=0; c.gridy=y;
        p.add(new JLabel(l),c);
        c.gridx=1;
        p.add(comp,c);
    }
}