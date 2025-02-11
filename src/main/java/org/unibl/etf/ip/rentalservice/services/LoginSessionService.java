package org.unibl.etf.ip.rentalservice.services;

import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.core.CrudService;
import org.unibl.etf.ip.rentalservice.model.dto.LoginSession;
import org.unibl.etf.ip.rentalservice.model.entities.LoginSessionEntity;
import org.unibl.etf.ip.rentalservice.model.requests.LoginSessionRequest;

import java.util.List;
import java.util.Optional;

public interface LoginSessionService extends CrudService<Integer> {
    // Find active sessions for a user
    List<LoginSession> findActiveSessionsByUserId(Integer userId);

    // Find all sessions for a user
    List<LoginSession> findAllSessionsByUserId(Integer userId);
}
