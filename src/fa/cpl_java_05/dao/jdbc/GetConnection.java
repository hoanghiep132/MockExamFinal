package fa.cpl_java_05.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetConnection {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "hiepnguyen132";
    private static final String URL = "jdbc:mysql://localhost:3306/Reading_Book_Management_System?autoReconnect=true&useSSL=false"
            + "&useUnicode=yes&characterEncoding=UTF-8";

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GetConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
