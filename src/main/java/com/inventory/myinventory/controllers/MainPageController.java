package com.inventory.myinventory.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;

@Controller
public class MainPageController {

    private final String url = "jdbc:mysql://localhost:3306/test";
    private final String user = "root";
    private final String password = "";

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println("Connection is Successful to the database " + url);
        return connection;
    }

    @RequestMapping("/main")
    public String showMainPage() {
        return "main";
    }

//    @PostMapping("/insertGoodsQuery")
//    public String insertGoodsQuery(@RequestParam("name") String name) {
//        try (Connection connection = getConnection()) {
//            int nextId = getNextId(connection);
//
//            String insertQuery = "INSERT INTO inventory(id, name) VALUES(?, ?)";
//            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
//                insertStatement.setInt(1, nextId);
//                insertStatement.setString(2, name);
//                insertStatement.executeUpdate();
//            }
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//        return "redirect:/main";
//    }
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