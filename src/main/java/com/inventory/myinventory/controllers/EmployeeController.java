package com.inventory.myinventory.controllers;

import com.inventory.myinventory.model.DatabaseConnection;
import com.inventory.myinventory.model.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private DatabaseConnection databaseConnection;

    @GetMapping("/employees")
    public String employees(Model model) {
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(databaseConnection.getUrl(),
                databaseConnection.getUsername(),
                databaseConnection.getPassword())) {

            String selectQuery = "SELECT employee_id, first_name, last_name FROM employees";

            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Employee employee = new Employee();
                        employee.setId(resultSet.getInt("employee_id"));
                        employee.setFirstName(resultSet.getString("first_name"));
                        employee.setLastName(resultSet.getString("last_name"));
                        employees.add(employee);
                    }
                }
            }

            model.addAttribute("employees", employees);
            return "employees";

        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Błąd połączenia z bazą danych: " + e.getMessage());
            return "error";
        }
    }
}
