package com.opencars.netgo.users.entity;

import java.util.ArrayList;
import java.util.List;

public class Organization {
    private Employee CEO;

    public Organization(Employee CEO) {
        this.CEO = CEO;
    }

    public List<Employee> getEmployeesInLine(int line) {
        List<Employee> employees = new ArrayList<>();
        if (line == 1) {
            employees.add(CEO);
        } else {
            for (Employee e : CEO.getChilds()) {
                employees.addAll(getEmployeesInLine(e, line - 1));
            }
        }
        return employees;
    }

    private List<Employee> getEmployeesInLine(Employee e, int line) {
        List<Employee> employees = new ArrayList<>();
        if (line == 1) {
            employees.add(e);
        } else {
            for (Employee sub : e.getChilds()) {
                employees.addAll(getEmployeesInLine(sub, line - 1));
            }
        }
        return employees;
    }
}

