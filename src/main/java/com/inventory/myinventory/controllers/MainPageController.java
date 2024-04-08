package com.inventory.myinventory.controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

            // Użyj parametryzowanego zapytania, aby uniknąć ataków SQL Injection
            String query = "INSERT INTO student(id, name) VALUES(1, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return "main";
    }
    // Metoda do wczytywania pliku HTML z zasobów aplikacji
    private String loadHTML(String fileName) throws IOException {
        Resource resource = new ClassPathResource(fileName);
        byte[] bytes = Files.readAllBytes(Paths.get(resource.getURI()));
        return new String(bytes);
    }
}