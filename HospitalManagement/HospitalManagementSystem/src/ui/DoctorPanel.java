package ui;

import dao.DoctorDAO;
import model.Doctor;

import javax.swing.*;
import java.awt.*;

public class DoctorPanel extends JPanel {

    private DoctorDAO dao = new DoctorDAO();
    private DefaultListModel<Doctor> listModel = new DefaultListModel<>();

    public DoctorPanel() {

        setLayout(new BorderLayout(10,10));

        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
        JTextField nameF = new JTextField();
        JTextField specF = new JTextField();
        JButton addBtn = new JButton("Add Doctor");

        form.add(new JLabel("Name:")); form.add(nameF);
        form.add(new JLabel("Specialization:")); form.add(specF);
        form.add(new JLabel("")); form.add(addBtn);

        JList<Doctor> list = new JList<>(listModel);

        add(new JScrollPane(list), BorderLayout.CENTER);
        add(form, BorderLayout.NORTH);

        addBtn.addActionListener(e -> {
            try {
                String name = nameF.getText().trim();
                String spec = specF.getText().trim();

                if (name.isEmpty() || spec.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Fields cannot be empty!");
                    return;
                }

                dao.save(new Doctor(name, spec));
                refresh();

                nameF.setText("");
                specF.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        refresh();
    }

    private void refresh() {
        try {
            listModel.clear();
            dao.findAll().forEach(listModel::addElement);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to load doctors.");
        }
    }
}
