package com.inventory.myinventory.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;

@RestController
public class MainPageController {
    @RequestMapping("/main")
    public String hello(){
        return "<html><body><h1>Hello World</h1><form action=\"/executeQuery\" method=\"post\">" +
                "<input type=\"text\" name=\"name\" placeholder=\"Enter Name\">" +
                "<button type=\"submit\">Execute Query</button></form></body></html>";
    }

    @PostMapping("/executeQuery")
    public String executeQuery(@RequestParam("name") String name){
        try {
            String url = "jdbc:mysql://localhost:3306/test";
            String user = "root";
            String password = "";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection is Successful to the database" + url);

            // Użyj parametryzowanego zapytania, aby uniknąć ataków SQL Injection
            String query = "INSERT INTO student(id, name) VALUES(1, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.executeUpdate();

            return "Query executed successfully!";
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return "Failed to execute query: " + e.getMessage();
        }
    }
}
