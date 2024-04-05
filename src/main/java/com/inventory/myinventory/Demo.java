package com.inventory.myinventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Demo {
    public static void main(String args[]) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection is Successful to the database" + url);
            String query = "Insert into student(id, name) values(1, 'Robert')";
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
