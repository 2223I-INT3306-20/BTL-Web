/*
package com.btl.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBC {

    private static String DB_URL = "jdbc:mysql://localhost:3306/productionMove";
    private static String USER_NAME = "root";
    private static String PASSWORD = "DinhM@nh3602";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            System.out.println("connect successfully!");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }
        return connection;
    }
}
*/
