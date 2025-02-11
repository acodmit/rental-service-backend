package org.unibl.etf.ip.rentalservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.ip.rentalservice.core.CrudController;
import org.unibl.etf.ip.rentalservice.model.dto.Employee;
import org.unibl.etf.ip.rentalservice.model.requests.EmployeeRequest;
import org.unibl.etf.ip.rentalservice.services.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController extends CrudController<Integer, EmployeeRequest, Employee> {

    public EmployeeController(final EmployeeService employeeService) {
        super(Employee.class, employeeService);
    }
}
