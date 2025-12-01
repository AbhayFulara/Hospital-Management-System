package dao;

import model.Doctor;
import util.DBConnection;
import util.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DoctorDAO {

    private static final Logger LOGGER = LoggerUtil.getLogger(DoctorDAO.class);

    public List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM doctors";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Doctor d = new Doctor();
                d.setDoctorId(rs.getInt("doctor_id"));
                d.setName(rs.getString("name"));
                d.setSpecialization(rs.getString("specialization"));
                d.setNotes(rs.getString("notes"));
                list.add(d);
            }
        } catch (SQLException e) {
            LOGGER.severe("Error fetching doctors: " + e.getMessage());
        }
        return list;
    }

    public void updateDoctorNotes(int doctorId, String notes) {
        String sql = "UPDATE doctors SET notes=? WHERE doctor_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, notes);
            ps.setInt(2, doctorId);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe("Error updating doctor notes: " + e.getMessage());
        }
    }
}
