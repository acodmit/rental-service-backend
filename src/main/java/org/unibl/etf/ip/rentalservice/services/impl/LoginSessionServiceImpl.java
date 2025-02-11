package org.unibl.etf.ip.rentalservice.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.model.dto.LoginSession;
import org.unibl.etf.ip.rentalservice.model.entities.LoginSessionEntity;
import org.unibl.etf.ip.rentalservice.repositories.LoginSessionEntityRepository;
import org.unibl.etf.ip.rentalservice.services.LoginSessionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LoginSessionServiceImpl extends CrudJpaService<LoginSessionEntity, Integer> implements LoginSessionService {

    private final LoginSessionEntityRepository loginSessionEntityRepository;

    public LoginSessionServiceImpl(LoginSessionEntityRepository loginSessionEntityRepository, ModelMapper modelMapper) {
        super(loginSessionEntityRepository, LoginSessionEntity.class, modelMapper);
        this.loginSessionEntityRepository = loginSessionEntityRepository;
    }

    @Override
    public List<LoginSession> findActiveSessionsByUserId(Integer userId) {
        List<LoginSessionEntity> activeSessions = loginSessionEntityRepository.findByUserIdAndLogoutTimeIsNull(userId);
        return activeSessions.stream()
                .map(session -> getModelMapper().map(session, LoginSession.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<LoginSession> findAllSessionsByUserId(Integer userId) {
        List<LoginSessionEntity> allSessions = loginSessionEntityRepository.findByUserId(userId);
        return allSessions.stream()
                .map(session -> getModelMapper().map(session, LoginSession.class))
                .collect(Collectors.toList());
    }
}