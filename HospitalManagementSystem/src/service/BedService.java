package service;

import dao.AppointmentDAO;
import dao.BedDAO;
import model.Appointment;
import model.Bed;
import util.DBConnection;
import util.LoggerUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class BedService {

    private static final Logger LOGGER = LoggerUtil.getLogger(BedService.class);

    private final BedDAO bedDAO = new BedDAO();
    private final AppointmentDAO apptDAO = new AppointmentDAO();

    public List<Bed> getAllBeds() {
        return bedDAO.getAllBeds();
    }

    public List<Bed> getAvailableBeds() {
        return bedDAO.getAvailableBeds();
    }

    // Transaction: assign bed + create appointment
    public boolean allotBedAndCreateAppointment(int bedId, int patientId, int doctorId,
                                                LocalDate date, String timeSlot) {
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            // 1) Update bed
            bedDAO.updateBedStatus(bedId, "OCCUPIED", patientId, con);

            // 2) Create appointment
            Appointment a = new Appointment();
            a.setPatientId(patientId);
            a.setDoctorId(doctorId);
            a.setDate(date);
            a.setTimeSlot(timeSlot);
            a.setStatus("SCHEDULED");
            apptDAO.addAppointment(a, con);

            con.commit();
            return true;
        } catch (SQLException e) {
            LOGGER.severe("Transaction failed: " + e.getMessage());
            return false;
        }
    }

    public boolean releaseBed(int bedId) {
        try (Connection con = DBConnection.getConnection()) {
            bedDAO.updateBedStatus(bedId, "AVAILABLE", null, con);
            return true;
        } catch (SQLException e) {
            LOGGER.severe("Error releasing bed: " + e.getMessage());
            return false;
        }
    }
}
