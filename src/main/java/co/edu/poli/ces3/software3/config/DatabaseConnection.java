package co.edu.poli.ces3.software3.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://database-ces3.cfecwycg8uud.us-east-1.rds.amazonaws.com:5432/postgres";
    private static final String USER = "Sebas";
    private static final String PASS = "foxypro1";

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}