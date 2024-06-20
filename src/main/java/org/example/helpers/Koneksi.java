package org.example.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    public static Connection getConnection(){
        Connection c = null;

        try {
            System.out.println("Connecting...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost/crud_perpustakaan", "root", "");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver class not found: " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database: " + e.getMessage(), e);
        }
        return c;
    }
}
