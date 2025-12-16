package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DBConnection {

    private static final String URL  = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String USER = "root";
    private static final String PASS = "Admin@123";

    private static final Logger LOGGER = LoggerUtil.getLogger(DBConnection.class);

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL 8+
        } catch (ClassNotFoundException e) {
            LOGGER.severe("MySQL JDBC Driver not found: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
