
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
}
