package org.unibl.etf.ip.rentalservice.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.exceptions.NotFoundException;
import org.unibl.etf.ip.rentalservice.model.dto.Client;
import org.unibl.etf.ip.rentalservice.model.dto.Employee;
import org.unibl.etf.ip.rentalservice.model.dto.User;
import org.unibl.etf.ip.rentalservice.model.entities.UserEntity;
import org.unibl.etf.ip.rentalservice.model.enums.UserType;
import org.unibl.etf.ip.rentalservice.repositories.UserEntityRepository;
import org.unibl.etf.ip.rentalservice.services.ClientService;
import org.unibl.etf.ip.rentalservice.services.EmployeeService;
import org.unibl.etf.ip.rentalservice.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl extends CrudJpaService<UserEntity, Integer> implements UserService {

    private final UserEntityRepository userEntityRepository;

    private final ClientService clientService;
    private final EmployeeService employeeService;

    public UserServiceImpl(UserEntityRepository userEntityRepository, ModelMapper modelMapper,
                           ClientService clientService, EmployeeService employeeService) {
        super(userEntityRepository, UserEntity.class, modelMapper);
        this.userEntityRepository = userEntityRepository;
        this.clientService = clientService;
        this.employeeService = employeeService;
    }

    @Override
    public User findByUsername(String username) {
        UserEntity userEntity = userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username " + username + " not found"));
        return getModelMapper().map(userEntity, User.class);
    }

    @Override
    public User findByEmail(String email) {
        UserEntity userEntity = userEntityRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email " + email + " not found"));
        return getModelMapper().map(userEntity, User.class);
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        UserEntity userEntity = userEntityRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new EntityNotFoundException("User with phone number " + phoneNumber + " not found"));
        return getModelMapper().map(userEntity, User.class);
    }

    public String getUserRole(Integer userId) {
        // Check if the user is a client
        List<Client> clients = clientService.findAll(Client.class);
        for (Client client : clients) {
            if (client.getId().equals(userId)) {
                return "CLIENT"; // User is a client
            }
        }

        // Check if the user is an employee and return role
        List<Employee> employees = employeeService.findAll(Employee.class);
        for (Employee employee : employees) {
            if (employee.getId().equals(userId)) {
                UserType role = employee.getRole();
                switch (role) {
                    case ADMIN:
                        return "ADMIN"; // User is an admin
                    case OPERATOR:
                        return "OPERATOR"; // User is an operator
                    case MANAGER:
                        return "MANAGER"; // User is a manager
                    default:
                        throw new IllegalArgumentException("Unknown role: " + role);
                }
            }
        }

        // If the user is not found in either Client or Employee services
        throw new NotFoundException("User with ID " + userId + " not found.");
    }
    /*@Override
    public List<User> findAllUsersWithClientAndEmployee() {
        List<UserEntity> userEntities = userEntityRepository.findAll();
        return userEntities.stream()
                .map(user -> {
                    User dto = getModelMapper().map(user, User.class);
                    if (user.getClient() != null) {
                        dto.setClientId(user.getClient().getId()); // Mapping ClientEntity ID
                    }
                    if (user.getEmployee() != null) {
                        dto.setEmployeeId(user.getEmployee().getId()); // Mapping EmployeeEntity ID
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }*/

}

