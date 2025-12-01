package service;

import dao.AppointmentDAO;
import model.Appointment;

import java.time.LocalDate;
import java.util.List;

public class AppointmentService {

    private final AppointmentDAO appointmentDAO = new AppointmentDAO();

    public void create(Appointment a) {
        try {
            appointmentDAO.addAppointment(a, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Appointment> getByDate(LocalDate date) {
        return appointmentDAO.getAppointmentsByDate(date);
    }

    public void updateStatus(int id, String status) {
        appointmentDAO.updateStatus(id, status);
    }
}
