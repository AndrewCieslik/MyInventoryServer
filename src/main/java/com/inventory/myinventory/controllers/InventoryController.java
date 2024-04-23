package com.inventory.myinventory.controllers;

import org.springframework.ui.Model;
import com.inventory.myinventory.model.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class InventoryController {

    @Autowired
    private DatabaseConnection databaseConnection;

    @GetMapping("/main")
    public String showMainPage(Model model) {
        List<String> regions = fetchRegions();
        model.addAttribute("regions", regions);
        return "main";
    }

    @PostMapping("/insertRegions")
    public String insertRegions(@RequestParam("region") String region) {
        try (Connection connection = DriverManager.getConnection(databaseConnection.getUrl(),
                databaseConnection.getUsername(),
                databaseConnection.getPassword())) {
            int nextId = getNextId(connection);

            String insertQuery = "INSERT INTO regions(region_id, region_name) VALUES(?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setInt(1, nextId);
                insertStatement.setString(2, region);
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/main";
    }

    private int getNextId(Connection connection) throws SQLException {
        String getMaxIdQuery = "SELECT MAX(region_id) FROM regions";
        try (PreparedStatement getMaxIdStatement = connection.prepareStatement(getMaxIdQuery);
             ResultSet resultSet = getMaxIdStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1) + 1;
            } else {
                return 1;
            }
        }
    }

    private List<String> fetchRegions() {
        List<String> regions = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseConnection.getUrl(),
                databaseConnection.getUsername(),
                databaseConnection.getPassword())) {
            String selectQuery = "SELECT region_name FROM regions";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    regions.add(resultSet.getString("region_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return regions;
    }
}
