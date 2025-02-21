package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.dto.User;
import org.unibl.etf.ip.rentalservice.model.entities.UserEntity;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

    // Check if user exists by username
    Boolean existsByUsername(String username);

    // Find by username
    Optional<UserEntity> findByUsername(String username);

    // Find by email
    Optional<UserEntity> findByEmail(String email);

    // Find by phone number
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
}
