package com.magdesiz.recursoshumanos.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static String URL = "jdbc:mysql://localhost:3306/login_in4bv";
    private static String USER = "IN4BV";
    private static String PASSWORD = "%IndiVA4";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
