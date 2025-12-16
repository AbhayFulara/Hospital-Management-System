package dao;

import model.Appointment;
import util.DBConnection;
import util.LoggerUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AppointmentDAO {

    private static final Logger LOGGER = LoggerUtil.getLogger(AppointmentDAO.class);

    public void addAppointment(Appointment a, Connection externalCon) throws SQLException {
        // if externalCon is not null, use it in transaction; else getConnection()
        boolean ownConnection = (externalCon == null);
        Connection con = externalCon != null ? externalCon : DBConnection.getConnection();

        String sql = "INSERT INTO appointments(patient_id, doctor_id, appointment_date, time_slot, status) " +
                     "VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, a.getPatientId());
            ps.setInt(2, a.getDoctorId());
            ps.setDate(3, Date.valueOf(a.getDate()));
            ps.setString(4, a.getTimeSlot());
            ps.setString(5, a.getStatus());
            ps.executeUpdate();
        } finally {
            if (ownConnection && con != null) con.close();
        }
    }

    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE appointment_date=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Appointment a = new Appointment();
                    a.setAppointmentId(rs.getInt("appointment_id"));
                    a.setPatientId(rs.getInt("patient_id"));
                    a.setDoctorId(rs.getInt("doctor_id"));
                    a.setDate(rs.getDate("appointment_date").toLocalDate());
                    a.setTimeSlot(rs.getString("time_slot"));
                    a.setStatus(rs.getString("status"));
                    list.add(a);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error fetching appointments: " + e.getMessage());
        }
        return list;
    }

    public void updateStatus(int appointmentId, String status) {
        String sql = "UPDATE appointments SET status=? WHERE appointment_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, appointmentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe("Error updating appointment status: " + e.getMessage());
        }
    }
}
