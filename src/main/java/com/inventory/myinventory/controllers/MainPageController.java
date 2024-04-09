package com.inventory.myinventory.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;

@Controller
public class MainPageController {
    @RequestMapping("/main")
    public String hello(){
        return "main";
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

            // Pobierz najwyższe ID z tabeli student
            String getMaxIdQuery = "SELECT MAX(id) FROM inventory";
            PreparedStatement getMaxIdStatement = connection.prepareStatement(getMaxIdQuery);
            ResultSet resultSet = getMaxIdStatement.executeQuery();

            int nextId = 1; // Domyślne ID, jeśli tabela jest pusta

            if (resultSet.next()) {
                // Jeśli są wyniki, pobierz najwyższe ID i zwiększ je o jeden
                int maxId = resultSet.getInt(1);
                nextId = maxId + 1;
            }

            // Użyto parametryzowanego zapytania, aby uniknąć ataków SQL Injection
            String insertQuery = "INSERT INTO inventory(id, name) VALUES(?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setInt(1, nextId); // Ustaw ID na kolejne dostępne
            insertStatement.setString(2, name);
            insertStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return "main";
    }

    @PostMapping("/eraseQuery")
    public String eraseQuery(){
        try {
            String url = "jdbc:mysql://localhost:3306/test";
            String user = "root";
            String password = "";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection is Successful to the database" + url);

            String query = "DELETE from inventory";
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return "main";
    }

}