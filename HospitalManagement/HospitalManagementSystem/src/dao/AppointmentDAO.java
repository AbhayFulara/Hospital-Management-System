package dao;

import model.Appointment;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    /**
     * Create appointment in a transaction:
     * 1) Insert appointment
     * 2) Mark bed as occupied
     */
    public Appointment createAppointmentTransactional(Appointment appt) throws SQLException {
        String insertSql = "INSERT INTO appointments (patient_id, doctor_id, appointment_time, bed_id) VALUES (?, ?, ?, ?)";
        String bedSql = "UPDATE beds SET occupied = ? WHERE id = ? AND occupied = FALSE";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement psInsert = c.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psBed = c.prepareStatement(bedSql)) {

            try {
                c.setAutoCommit(false);

                // mark bed as occupied (ensures bed was free)
                psBed.setBoolean(1, true);
                psBed.setInt(2, appt.getBedId());
                int updated = psBed.executeUpdate();

                if (updated == 0) {
                    // bed was not free
                    c.rollback();
                    throw new SQLException("Selected bed is already occupied.");
                }

                psInsert.setInt(1, appt.getPatientId());
                psInsert.setInt(2, appt.getDoctorId());
                psInsert.setTimestamp(3, Timestamp.valueOf(appt.getAppointmentTime()));
                psInsert.setInt(4, appt.getBedId());
                psInsert.executeUpdate();

                try (ResultSet rs = psInsert.getGeneratedKeys()) {
                    if (rs.next()) {
                        // set id back (not strictly necessary)
                    }
                }

                c.commit();
                return appt;
            } catch (SQLException ex) {
                c.rollback();
                throw ex;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }

    public List<Appointment> findAll() throws SQLException {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Appointment(
                    rs.getInt("id"),
                    rs.getInt("patient_id"),
                    rs.getInt("doctor_id"),
                    rs.getInt("bed_id"),
                    rs.getTimestamp("appointment_time").toLocalDateTime()
                ));
            }
        }
        return list;
    }
}
