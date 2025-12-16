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

    // INSERT
    public void insertDoctor(Doctor doctor) {
        String sql = "INSERT INTO doctors (name, specialization, notes) VALUES (?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, doctor.getName());
            ps.setString(2, doctor.getSpecialization());
            ps.setString(3, doctor.getNotes());
            ps.executeUpdate();

        } catch (SQLException e) {
            LOGGER.severe("Insert doctor failed: " + e.getMessage());
        }
    }

    // UPDATE
    public void updateDoctor(Doctor doctor) {
        String sql = "UPDATE doctors SET name=?, specialization=?, notes=? WHERE doctor_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, doctor.getName());
            ps.setString(2, doctor.getSpecialization());
            ps.setString(3, doctor.getNotes());
            ps.setInt(4, doctor.getDoctorId());
            ps.executeUpdate();

        } catch (SQLException e) {
            LOGGER.severe("Update doctor failed: " + e.getMessage());
        }
    }

    // DELETE
    public void deleteDoctor(int doctorId) {
        String sql = "DELETE FROM doctors WHERE doctor_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, doctorId);
            ps.executeUpdate();

        } catch (SQLException e) {
            LOGGER.severe("Delete doctor failed: " + e.getMessage());
        }
    }

    // SELECT
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
            LOGGER.severe("Fetch doctors failed: " + e.getMessage());
        }
        return list;
    }
}