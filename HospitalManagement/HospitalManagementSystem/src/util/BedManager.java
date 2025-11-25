package util;

import model.Bed;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A very small thread that periodically checks bed availability and prints it.
 * Demonstrates multithreading and safe DB access.
 */
public class BedManager extends Thread {
    private volatile boolean running = true;

    public void shutdown() { running = false; }

    @Override
    public void run() {
        while (running) {
            try {
                List<Bed> beds = fetchBeds();
                System.out.println("Bed status update:");
                for (Bed b : beds) System.out.println("  " + b);
                Thread.sleep(10_000);
            } catch (Exception ex) {
                System.err.println("BedManager error: " + ex.getMessage());
            }
        }
    }

    private List<Bed> fetchBeds() throws SQLException {
        List<Bed> list = new ArrayList<>();
        String sql = "SELECT * FROM beds";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Bed(rs.getInt("id"), rs.getString("bed_number"), rs.getBoolean("occupied")));
            }
        }
        return list;
    }
}
