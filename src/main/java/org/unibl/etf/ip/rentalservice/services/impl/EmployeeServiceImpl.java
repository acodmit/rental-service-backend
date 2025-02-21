package org.unibl.etf.ip.rentalservice.services.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.model.dto.Client;
import org.unibl.etf.ip.rentalservice.model.dto.Employee;
import org.unibl.etf.ip.rentalservice.model.entities.ClientEntity;
import org.unibl.etf.ip.rentalservice.model.entities.EmployeeEntity;
import org.unibl.etf.ip.rentalservice.model.enums.UserType;
import org.unibl.etf.ip.rentalservice.model.requests.ClientRequest;
import org.unibl.etf.ip.rentalservice.model.requests.EmployeeRequest;
import org.unibl.etf.ip.rentalservice.repositories.EmployeeEntityRepository;
import org.unibl.etf.ip.rentalservice.repositories.UserEntityRepository;
import org.unibl.etf.ip.rentalservice.services.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl extends CrudJpaService<EmployeeEntity, Integer> implements EmployeeService {

    private final EmployeeEntityRepository employeeEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public EmployeeServiceImpl(EmployeeEntityRepository employeeEntityRepository, ModelMapper modelMapper,
                               UserEntityRepository userEntityRepository) {
        super(employeeEntityRepository, EmployeeEntity.class, modelMapper);
        this.employeeEntityRepository = employeeEntityRepository;
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public List<Employee> findByRole(UserType role) {
        List<EmployeeEntity> employees = employeeEntityRepository.findByRole(role);
        return employees.stream()
                .map(employee -> getModelMapper().map(employee, Employee.class))
                .collect(Collectors.toList());
    }

    @Override
    public Employee insertEmployee(EmployeeRequest request) {
        if(userEntityRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        EmployeeEntity employeeEntity = getModelMapper().map(request, EmployeeEntity.class);
        employeeEntity.setId(null); // Ensure it's a new entity
        employeeEntity.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        employeeEntity = employeeEntityRepository.saveAndFlush(employeeEntity);
        getEntityManager().refresh(employeeEntity);
        return getModelMapper().map(employeeEntity, Employee.class);
    }

}
