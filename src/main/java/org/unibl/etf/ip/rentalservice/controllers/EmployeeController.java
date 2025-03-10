package org.unibl.etf.ip.rentalservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.ip.rentalservice.core.CrudController;
import org.unibl.etf.ip.rentalservice.exceptions.NotFoundException;
import org.unibl.etf.ip.rentalservice.model.dto.Client;
import org.unibl.etf.ip.rentalservice.model.dto.Employee;
import org.unibl.etf.ip.rentalservice.model.requests.ClientRequest;
import org.unibl.etf.ip.rentalservice.model.requests.EmployeeRequest;
import org.unibl.etf.ip.rentalservice.services.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController extends CrudController<Integer, EmployeeRequest, Employee> {

    private final EmployeeService employeeService;

    public EmployeeController(final EmployeeService employeeService) {
        super(Employee.class, employeeService);
        this.employeeService = employeeService;
    }

    @Override
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody EmployeeRequest request) throws NotFoundException {
        Employee response = employeeService.insertEmployee(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
