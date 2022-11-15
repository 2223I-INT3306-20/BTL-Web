package com.btl.database;

import com.btl.entity.Account;
import com.btl.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsersData {
    private static String DB_URL = "jdbc:mysql://localhost:3306/productionMove";
    private static String USER_NAME = "root";
    private static String PASSWORD = "DinhM@nh3602";

    private List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public static Connection getConnection(String dbURL, String userName,
                                           String password) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, userName, password);
            System.out.println("connect successfully!");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }
        return conn;
    }

    public void getAllUser() {
        try {
            Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from account");
            while (rs.next()) {
                User user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                users.add(user);
            }
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public User getUserById(Account account) {
        getAllUser();
        for (int i = 0; i < users.size(); i++) {
            if (account.getAcc().equals(users.get(i).getId())) {
                return users.get(i);
            }
        }

        return null;
    }
}