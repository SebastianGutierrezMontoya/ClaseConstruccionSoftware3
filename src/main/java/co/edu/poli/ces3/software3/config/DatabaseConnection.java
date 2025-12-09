package co.edu.poli.ces3.software3.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

//    private static final String URL = "jdbc:postgresql://database-ces3.cfecwycg8uud.us-east-1.rds.amazonaws.com:5432/postgres";
//    private static final String USER = "ces3admin";
//    private static final String PASS = "qwert12345";

    private static final String URL  = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASS = System.getenv("DB_PASS");

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close(); // libera la conexión
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión");
                e.printStackTrace();
            }
        }
    }

}