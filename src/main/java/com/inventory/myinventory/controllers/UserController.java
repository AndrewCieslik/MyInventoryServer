package com.inventory.myinventory.controllers;

import com.inventory.myinventory.model.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.*;

@Controller
public class UserController {

    @Autowired
    private DatabaseConnection databaseConnection;

    @PostMapping("/insertUser")
    public String insertUser(@RequestParam("first_name") String first_name, @RequestParam("last_name") String last_name, @RequestParam("email") String email) {
        try (Connection connection = DriverManager.getConnection(databaseConnection.getUrl(),
                databaseConnection.getUsername(),
                databaseConnection.getPassword())) {
            int nextId = getNextId(connection);

            String insertQuery = "INSERT INTO employees(employee_id, first_name, last_name,email,phone_number,hire_date,job_id,salary,commission_pct,manager_id,department_id) VALUES(?,?,?,?,123456,'20/03/15','AD_VP',1234,null,null,null)";

            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setInt(1, nextId);
                insertStatement.setString(2, first_name);
                insertStatement.setString(3, last_name);
                insertStatement.setString(4, email);

                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/main";
    }

    @PostMapping("/eraseAllUsers")
    public String deleteUsers() {
        try (Connection connection = DriverManager.getConnection(databaseConnection.getUrl(),
                databaseConnection.getUsername(),
                databaseConnection.getPassword())) {

            String insertQuery = "DELETE from employees";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/main";
    }

    private int getNextId(Connection connection) throws SQLException {
        String getMaxIdQuery = "SELECT MAX(employee_id) FROM employees";
        try (PreparedStatement getMaxIdStatement = connection.prepareStatement(getMaxIdQuery);
             ResultSet resultSet = getMaxIdStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1) + 1;
            } else {
                return 1;
            }
        }
    }
    @GetMapping("/getUser/{employee_id}")
    @ResponseBody
    public String getUser(@PathVariable("employee_id") int employee_id) {
        try (Connection connection = DriverManager.getConnection(databaseConnection.getUrl(),
                databaseConnection.getUsername(),
                databaseConnection.getPassword())) {

            String selectQuery = "SELECT first_name,last_name,email FROM employees WHERE employee_id = ?";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                selectStatement.setInt(1, employee_id);

                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String first_name = resultSet.getString("first_name");
                        String last_name = resultSet.getString("last_name");
                        String email = resultSet.getString("email");
                        return "First Name: " + first_name + ", Last Name: " + last_name + ", Email: " + email;
                    } else {
                        return "User not found";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error occurred while fetching user";
        }
    }
}
