package com.inventory.myinventory.controllers;

import org.springframework.ui.Model;
import com.inventory.myinventory.model.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AssignController {

    @Autowired
    private DatabaseConnection databaseConnection;

    @PostMapping("/assignGoods")
    public String assignGoods(@RequestParam("user_id") String user_id, @RequestParam("id") String id) {
        try (Connection connection = DriverManager.getConnection(databaseConnection.getUrl(), databaseConnection.getUsername(), databaseConnection.getPassword())) {

            String insertQuery = "INSERT INTO assigned_goods(user_id, id) VALUES(?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1, user_id);
                insertStatement.setString(2, id);
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/main";
    }

    @PostMapping("/showAssignedItems")
    public String showAssignedItems(@RequestParam("user_id") int userId, Model model) {
        try (Connection connection = DriverManager.getConnection(databaseConnection.getUrl(), databaseConnection.getUsername(), databaseConnection.getPassword())) {
            String selectQuery = "SELECT * FROM assigned_goods WHERE user_id = ?";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                selectStatement.setInt(1, userId);
                ResultSet resultSet = selectStatement.executeQuery();

                List<String> assignedItems = new ArrayList<>();
                while (resultSet.next()) {
                    assignedItems.add(resultSet.getString("id"));
                }
                model.addAttribute("assignedItems", assignedItems);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "assigned_items"; // Assuming you have a template named assigned_items.html to display the list
    }
}
