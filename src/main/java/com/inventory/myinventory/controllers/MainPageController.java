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
//        return "main";
//    }

    @PostMapping("/eraseGoodsQuery")
    public String eraseQuery() {
        try (Connection connection = getConnection()) {
            String query = "DELETE from inventory";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(query);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return "main";
    }

//    @PostMapping("/insertUserQuery")
//    public String insertUserQuery(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
//        try (Connection connection = getConnection()) {
//            int nextId = getNextId(connection);
//
//            String insertQuery = "INSERT INTO users(user_id, LastName, FirstName) VALUES(?, ?, ?)";
//            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
//                insertStatement.setInt(1, nextId);
//                insertStatement.setString(2, lastName);
//                insertStatement.setString(3, firstName);
//                insertStatement.executeUpdate();
//            }
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//        return "main";
//    }

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
        return "main";
    }


}