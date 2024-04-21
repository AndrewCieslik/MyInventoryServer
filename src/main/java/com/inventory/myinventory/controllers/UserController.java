package com.inventory.myinventory.controllers;

import com.inventory.myinventory.model.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;

@Controller
public class UserController {

    @Autowired
    private DatabaseConnection databaseConnection;

    @PostMapping("/insertUser")
    public String insertUser(@RequestParam("first_name") String first_name, @RequestParam("last_name") String last_name) {
        try (Connection connection = DriverManager.getConnection(databaseConnection.getUrl(),
                databaseConnection.getUsername(),
                databaseConnection.getPassword())) {
            int nextId = getNextId(connection);

            //String insertQuery = "INSERT INTO employees(employee_id, first_name, last_name) VALUES(?, ?, ?)";
            String insertQuery = "INSERT INTO employees(employee_id, first_name, last_name,email,phone_number,hire_date,job_id,salary,commission_pct,manager_id,department_id) VALUES(22,'And','Cie','mail',123456,'20/03/15','AD_VP',1234,null,null,null)";
            //"INSERT INTO employees(employee_id, first_name, last_name,email,phone_number,hire_date,job_id,salary,commission_pct,manager_id,department_id)"
            //VALUES (21,'And','Cie','mail',123456,'20/03/15','AD_VP',1234,null,null,null);
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setInt(1, nextId);
                insertStatement.setString(2, first_name);
                insertStatement.setString(3, last_name);
                insertStatement.executeUpdate();
            }
            String insertCommit = "commit";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertCommit)) {
                insertStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/main";
    }

    @PostMapping("/eraseAllUsers")
    public String deleteUser() {
        try (Connection connection = DriverManager.getConnection(databaseConnection.getUrl(),
                databaseConnection.getUsername(),
                databaseConnection.getPassword())) {
            int nextId = getNextId(connection);

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
        String getMaxIdQuery = "SELECT MAX(id) FROM employees";
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
