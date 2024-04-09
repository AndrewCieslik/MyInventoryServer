package com.inventory.myinventory.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;

@Controller
public class UserController {

    private final String url = "jdbc:mysql://localhost:3306/test";
    private final String user = "root";
    private final String password = "";

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println("Connection is Successful to the database " + url);
        return connection;
    }

    // Metoda wstawiająca użytkownika do bazy danych
    @PostMapping("/insertUserQuery")
    public String insertUserQuery(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        try (Connection connection = getConnection()) {
            int nextId = getNextId(connection);

            String insertQuery = "INSERT INTO users(user_id, LastName, FirstName) VALUES(?, ?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setInt(1, nextId);
                insertStatement.setString(2, lastName);
                insertStatement.setString(3, firstName);
                insertStatement.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return "main";
    }
    private int getNextId(Connection connection) throws SQLException {
        String getMaxIdQuery = "SELECT MAX(id) FROM inventory";
        try (PreparedStatement getMaxIdStatement = connection.prepareStatement(getMaxIdQuery);
             ResultSet resultSet = getMaxIdStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1) + 1;
            } else {
                return 1;
            }
        }
    }
}
