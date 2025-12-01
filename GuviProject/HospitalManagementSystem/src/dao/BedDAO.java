package dao;

import model.Bed;
import util.DBConnection;
import util.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BedDAO {

    private static final Logger LOGGER = LoggerUtil.getLogger(BedDAO.class);

    public List<Bed> getAllBeds() {
        List<Bed> list = new ArrayList<>();
        String sql = "SELECT * FROM beds";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Bed b = new Bed();
                b.setBedId(rs.getInt("bed_id"));
                b.setWard(rs.getString("ward"));
                b.setBedNumber(rs.getString("bed_number"));
                b.setStatus(rs.getString("status"));
                int pid = rs.getInt("patient_id");
                b.setPatientId(rs.wasNull() ? null : pid);
                list.add(b);
            }
        } catch (SQLException e) {
            LOGGER.severe("Error fetching beds: " + e.getMessage());
        }
        return list;
    }

    public List<Bed> getAvailableBeds() {
        List<Bed> list = new ArrayList<>();
        String sql = "SELECT * FROM beds WHERE status='AVAILABLE'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Bed b = new Bed();
                b.setBedId(rs.getInt("bed_id"));
                b.setWard(rs.getString("ward"));
                b.setBedNumber(rs.getString("bed_number"));
                b.setStatus(rs.getString("status"));
                list.add(b);
            }
        } catch (SQLException e) {
            LOGGER.severe("Error fetching available beds: " + e.getMessage());
        }
        return list;
    }

    public void updateBedStatus(int bedId, String status, Integer patientId, Connection con) throws SQLException {
        String sql = "UPDATE beds SET status=?, patient_id=? WHERE bed_id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            if (patientId == null) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, patientId);
            }
            ps.setInt(3, bedId);
            ps.executeUpdate();
        }
    }
}
