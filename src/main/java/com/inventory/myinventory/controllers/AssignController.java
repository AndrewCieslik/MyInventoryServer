package com.inventory.myinventory.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;

@Controller
public class AssignController {
    private final String url = "jdbc:mysql://localhost:3306/test";
    private final String user = "root";
    private final String password = "";

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println("Connection is Successful to the database " + url);
        return connection;
    }

    @PostMapping("/assignGoodsQuery")
    public String assignGoodsQuery(@RequestParam("user_id") String user_id, @RequestParam("id") String id) {
        try (Connection connection = getConnection()) {

            String insertQuery = "INSERT INTO assigned_goods(user_id, id) VALUES(?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1, user_id);
                insertStatement.setString(2, id);
                insertStatement.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/main";
    }
}
