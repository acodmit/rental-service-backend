package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.dto.User;

public interface UserEntityRepository extends JpaRepository<User, Integer> {
    // Find by username
    User findByUsername(String username);

    // Find by email
    User findByEmail(String email);

    // Find by phone number
    User findByPhoneNumber(String phoneNumber);
}
