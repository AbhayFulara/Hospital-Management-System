package dao;

import model.Patient;
import util.DBConnection;
import util.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PatientDAO {

    private static final Logger LOGGER = LoggerUtil.getLogger(PatientDAO.class);

    public void addPatient(Patient p) {
        String sql = "INSERT INTO patients(name, age, gender, phone, address) VALUES (?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setInt(2, p.getAge());
            ps.setString(3, p.getGender());
            ps.setString(4, p.getPhone());
            ps.setString(5, p.getAddress());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe("Error adding patient: " + e.getMessage());
        }
    }

    public void updatePatient(Patient p) {
        String sql = "UPDATE patients SET name=?, age=?, gender=?, phone=?, address=? WHERE patient_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setInt(2, p.getAge());
            ps.setString(3, p.getGender());
            ps.setString(4, p.getPhone());
            ps.setString(5, p.getAddress());
            ps.setInt(6, p.getPatientId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe("Error updating patient: " + e.getMessage());
        }
    }

    public void deletePatient(int id) {
        String sql = "DELETE FROM patients WHERE patient_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe("Error deleting patient: " + e.getMessage());
        }
    }

    public List<Patient> getAllPatients() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Patient p = new Patient();
                p.setPatientId(rs.getInt("patient_id"));
                p.setName(rs.getString("name"));
                p.setAge(rs.getInt("age"));
                p.setGender(rs.getString("gender"));
                p.setPhone(rs.getString("phone"));
                p.setAddress(rs.getString("address"));
                list.add(p);
            }
        } catch (SQLException e) {
            LOGGER.severe("Error fetching patients: " + e.getMessage());
        }
        return list;
    }

    public List<Patient> searchByName(String namePattern) {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients WHERE name LIKE ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + namePattern + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Patient p = new Patient();
                    p.setPatientId(rs.getInt("patient_id"));
                    p.setName(rs.getString("name"));
                    p.setAge(rs.getInt("age"));
                    p.setGender(rs.getString("gender"));
                    p.setPhone(rs.getString("phone"));
                    p.setAddress(rs.getString("address"));
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error searching patients: " + e.getMessage());
        }
        return list;
    }
}
