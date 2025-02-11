package org.unibl.etf.ip.rentalservice.services.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.model.dto.Employee;
import org.unibl.etf.ip.rentalservice.model.entities.EmployeeEntity;
import org.unibl.etf.ip.rentalservice.model.enums.UserType;
import org.unibl.etf.ip.rentalservice.repositories.EmployeeEntityRepository;
import org.unibl.etf.ip.rentalservice.services.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl extends CrudJpaService<EmployeeEntity, Integer> implements EmployeeService {

    private final EmployeeEntityRepository employeeEntityRepository;

    public EmployeeServiceImpl(EmployeeEntityRepository employeeEntityRepository, ModelMapper modelMapper) {
        super(employeeEntityRepository, EmployeeEntity.class, modelMapper);
        this.employeeEntityRepository = employeeEntityRepository;
    }

    @Override
    public List<Employee> findByRole(UserType role) {
        List<EmployeeEntity> employees = employeeEntityRepository.findByRole(role);
        return employees.stream()
                .map(employee -> getModelMapper().map(employee, Employee.class))
                .collect(Collectors.toList());
    }

//    @Override
//    public List<Employee> findAllEmployeesWithUsers() {
//        List<EmployeeEntity> employees = employeeEntityRepository.findAll();
//        return employees.stream()
//                .map(employee -> {
//                    Employee dto = getModelMapper().map(employee, Employee.class);
//                    dto.setId(employee.getUser().getId()); // Map UserEntity ID
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }
}
