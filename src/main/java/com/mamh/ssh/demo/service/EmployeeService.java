package com.mamh.ssh.demo.service;

import com.mamh.ssh.demo.dao.EmployeeDao;
import com.mamh.ssh.demo.entities.Employee;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class EmployeeService {
    private EmployeeDao employeeDao;

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public List<Employee> getAll() {
        return employeeDao.getAll();
    }

}
