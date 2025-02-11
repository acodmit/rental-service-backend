package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.entities.LoginSessionEntity;

import java.util.List;

public interface LoginSessionEntityRepository extends JpaRepository<LoginSessionEntity, Integer> {
    // Find active sessions for a user
    List<LoginSessionEntity> findByUserIdAndLogoutTimeIsNull(Integer userId);

    // Find all sessions by user ID
    List<LoginSessionEntity> findByUserId(Integer userId);
}
