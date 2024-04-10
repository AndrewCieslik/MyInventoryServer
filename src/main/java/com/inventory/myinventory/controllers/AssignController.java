package com.inventory.myinventory.controllers;

import com.inventory.myinventory.model.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;

@Controller
public class AssignController {

    @Autowired
    private DatabaseConnection databaseConnection;

    @PostMapping("/assignGoodsQuery")
    public String assignGoodsQuery(@RequestParam("user_id") String user_id, @RequestParam("id") String id) {
        try (Connection connection = DriverManager.getConnection(databaseConnection.getUrl(),
                databaseConnection.getUsername(),
                databaseConnection.getPassword())) {

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
}
