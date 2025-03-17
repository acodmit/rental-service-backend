package org.unibl.etf.ip.rentalservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.rentalservice.core.CrudController;
import org.unibl.etf.ip.rentalservice.exceptions.NotFoundException;
import org.unibl.etf.ip.rentalservice.model.dto.Employee;
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

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Integer id, @RequestBody EmployeeRequest request) throws NotFoundException {
        Employee response = employeeService.updateEmployee(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
