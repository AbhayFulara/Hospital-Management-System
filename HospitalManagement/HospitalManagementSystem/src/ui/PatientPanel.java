package ui;

import dao.PatientDAO;
import model.Patient;

import javax.swing.*;
import java.awt.*;

public class PatientPanel extends JPanel {

    private PatientDAO dao = new PatientDAO();
    private DefaultListModel<Patient> listModel = new DefaultListModel<>();

    public PatientPanel() {

        setLayout(new BorderLayout(10,10));

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        JTextField nameF = new JTextField();
        JTextField ageF = new JTextField();
        JTextField genderF = new JTextField();
        JButton addBtn = new JButton("Add Patient");

        form.add(new JLabel("Name:")); form.add(nameF);
        form.add(new JLabel("Age:")); form.add(ageF);
        form.add(new JLabel("Gender:")); form.add(genderF);
        form.add(new JLabel("")); form.add(addBtn);

        JList<Patient> list = new JList<>(listModel);
        list.setFont(new Font("Arial", Font.PLAIN, 14));

        add(new JScrollPane(list), BorderLayout.CENTER);
        add(form, BorderLayout.NORTH);

        addBtn.addActionListener(e -> {
            try {
                String name = nameF.getText().trim();
                String gender = genderF.getText().trim();
                int age = Integer.parseInt(ageF.getText().trim());

                if (name.isEmpty() || gender.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required.");
                    return;
                }

                dao.save(new Patient(name, age, gender));
                refresh();

                nameF.setText(""); ageF.setText(""); genderF.setText("");

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
            JOptionPane.showMessageDialog(this, "Failed to load patients.");
        }
    }
}
