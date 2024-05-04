package com.inventory.myinventory.model;

public class Employee {
    private int employee_id;
    private String first_name;
    private String last_name;

    public Employee() {
    }

    public Employee(int employee_id, String first_name, String last_name) {
        this.employee_id = employee_id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public int getEmployeeId() {
        return employee_id;
    }

    public void setId(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }
}
