package org.unibl.etf.ip.rentalservice.services;

import org.unibl.etf.ip.rentalservice.core.CrudService;
import org.unibl.etf.ip.rentalservice.model.dto.Employee;
import org.unibl.etf.ip.rentalservice.model.enums.UserType;

import java.util.List;


public interface EmployeeService extends CrudService<Integer> {
    // Find employees by role
    List<Employee> findByRole(UserType role);

    // Get all employees with user details
    //List<Employee> findAllEmployeesWithUsers();
}
