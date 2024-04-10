package com.inventory.myinventory.controllers;

import com.inventory.myinventory.model.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;

@Controller
public class InventoryController {

    @Autowired
    private DatabaseConnection databaseConnection;

    @PostMapping("/insertGoodsQuery")
    public String insertGoodsQuery(@RequestParam("name") String name) {
        try (Connection connection = DriverManager.getConnection(databaseConnection.getUrl(),
                databaseConnection.getUsername(),
                databaseConnection.getPassword())) {
            int nextId = getNextId(connection);

            String insertQuery = "INSERT INTO inventory(id, name) VALUES(?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setInt(1, nextId);
                insertStatement.setString(2, name);
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
