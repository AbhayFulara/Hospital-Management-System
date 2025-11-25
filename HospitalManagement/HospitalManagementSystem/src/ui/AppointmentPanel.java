package ui;

import dao.AppointmentDAO;
import dao.DoctorDAO;
import dao.PatientDAO;
import model.Appointment;
import model.Bed;
import model.Doctor;
import model.Patient;
import util.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;

public class AppointmentPanel extends JPanel {

    private PatientDAO pdao = new PatientDAO();
    private DoctorDAO ddao = new DoctorDAO();
    private AppointmentDAO adao = new AppointmentDAO();

    private JComboBox<Patient> patientBox = new JComboBox<>();
    private JComboBox<Doctor> doctorBox = new JComboBox<>();
    private JComboBox<Bed> bedBox = new JComboBox<>();
    private DefaultListModel<String> listModel = new DefaultListModel<>();

    public AppointmentPanel() {

        setLayout(new BorderLayout(10,10));

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        JButton bookBtn = new JButton("Book Appointment");

        form.add(new JLabel("Patient:")); form.add(patientBox);
        form.add(new JLabel("Doctor:")); form.add(doctorBox);
        form.add(new JLabel("Bed (Optional):")); form.add(bedBox);
        form.add(new JLabel("")); form.add(bookBtn);

        JList<String> list = new JList<>(listModel);
        add(form, BorderLayout.NORTH);
        add(new JScrollPane(list), BorderLayout.CENTER);

        bookBtn.addActionListener(e -> bookAppointment());
        refresh();
    }

    private void bookAppointment() {
        try {
            Patient p = (Patient) patientBox.getSelectedItem();
            Doctor d = (Doctor) doctorBox.getSelectedItem();
            Bed bed = (Bed) bedBox.getSelectedItem();

            if (p == null || d == null) {
                JOptionPane.showMessageDialog(this, "Please select both patient and doctor.");
                return;
            }

            int bedId = (bed != null) ? bed.getId() : 0;

            Appointment appt = new Appointment(
                    p.getId(),
                    d.getId(),
                    bedId,
                    LocalDateTime.now().plusDays(1)
            );

            adao.createAppointmentTransactional(appt);
            refresh();

            JOptionPane.showMessageDialog(this, "Appointment Booked Successfully!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void refresh() {
        try {
            patientBox.removeAllItems();
            doctorBox.removeAllItems();
            bedBox.removeAllItems();
            listModel.clear();

            pdao.findAll().forEach(patientBox::addItem);
            ddao.findAll().forEach(doctorBox::addItem);

            // Load available beds
            try (Connection c = DBConnection.getConnection();
                 PreparedStatement ps = c.prepareStatement("SELECT * FROM beds WHERE occupied = FALSE");
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    bedBox.addItem(new Bed(rs.getInt("id"), rs.getString("bed_number"), false));
                }
            }

            adao.findAll().forEach(a ->
                    listModel.addElement(
                            "Appt ID: " + a.getId() +
                                    " | Patient: " + a.getPatientId() +
                                    " | Doctor: " + a.getDoctorId() +
                                    " | Bed: " + a.getBedId() +
                                    " | Time: " + a.getAppointmentTime()
                    )
            );

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Refresh failed: " + ex.getMessage());
        }
    }
}
