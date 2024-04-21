package com.inventory.myinventory.controllers;

import com.inventory.myinventory.model.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;

@Controller
public class UserController {

    @Autowired
    private DatabaseConnection databaseConnection;

    @PostMapping("/insertUser")
    public String insertUser(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        try (Connection connection = DriverManager.getConnection(databaseConnection.getUrl(),
                databaseConnection.getUsername(),
                databaseConnection.getPassword())) {
            int nextId = getNextId(connection);

            String insertQuery = "INSERT INTO users(user_id, lastName, firstName) VALUES(?, ?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setInt(1, nextId);
                insertStatement.setString(2, lastName);
                insertStatement.setString(3, firstName);
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/main";
    }

    @PostMapping("/eraseAllUsers")
    public String insertUser() {
        try (Connection connection = DriverManager.getConnection(databaseConnection.getUrl(),
                databaseConnection.getUsername(),
                databaseConnection.getPassword())) {
            int nextId = getNextId(connection);

            String insertQuery = "DELETE from users";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/main";
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
