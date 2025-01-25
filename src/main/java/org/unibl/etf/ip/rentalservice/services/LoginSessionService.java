package org.unibl.etf.ip.rentalservice.services;

import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.core.CrudService;
import org.unibl.etf.ip.rentalservice.model.dto.LoginSession;
import org.unibl.etf.ip.rentalservice.model.entities.LoginSessionEntity;
import org.unibl.etf.ip.rentalservice.model.requests.LoginSessionRequest;

import java.util.List;
import java.util.Optional;

public interface LoginSessionService {
    LoginSessionEntity save(LoginSessionEntity loginSession);

    Optional<LoginSessionEntity> findById(Integer id);

    List<LoginSessionEntity> findAll();

    LoginSessionEntity update(Integer id, LoginSessionEntity updatedSession);

    void delete(Integer id);

    List<LoginSession> findByUserId(Integer userId);
}
