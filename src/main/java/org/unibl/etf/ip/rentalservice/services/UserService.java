package org.unibl.etf.ip.rentalservice.services;

import org.unibl.etf.ip.rentalservice.core.CrudService;
import org.unibl.etf.ip.rentalservice.model.dto.User;

public interface UserService extends CrudService<Integer> {
    // Find user by username
    User getUserInfo(String username);

    // Find user by email
    User findByEmail(String email);

    // Find user by phone number
    User findByPhoneNumber(String phoneNumber);

    // Get all users with their client and employee details
    // List<User> findAllUsersWithClientAndEmployee();

    // Get user role
    String getUserRole(Integer userId);

}

