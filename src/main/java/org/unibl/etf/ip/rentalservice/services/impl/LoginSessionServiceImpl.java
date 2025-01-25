package org.unibl.etf.ip.rentalservice.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.model.dto.LoginSession;
import org.unibl.etf.ip.rentalservice.model.entities.LoginSessionEntity;
import org.unibl.etf.ip.rentalservice.repositories.LoginSessionEntityRepository;
import org.unibl.etf.ip.rentalservice.services.LoginSessionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LoginSessionServiceImpl extends CrudJpaService<LoginSessionEntity, Integer> implements LoginSessionService {

    private final LoginSessionEntityRepository loginSessionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LoginSessionServiceImpl(LoginSessionEntityRepository loginSessionRepository, ModelMapper modelMapper) {
        super(loginSessionRepository, LoginSessionEntity.class, modelMapper);
        this.loginSessionRepository = loginSessionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LoginSessionEntity save(LoginSessionEntity loginSession) {
        return loginSessionRepository.save(loginSession);
    }

    @Override
    public Optional<LoginSessionEntity> findById(Integer id) {
        return loginSessionRepository.findById(id);
    }

    @Override
    public List<LoginSessionEntity> findAll() {
        return loginSessionRepository.findAll();
    }

    @Override
    public LoginSessionEntity update(Integer id, LoginSessionEntity updatedSession) {
        if (!loginSessionRepository.existsById(id)) {
            throw new IllegalArgumentException("LoginSession with ID " + id + " does not exist.");
        }
        updatedSession.setId(id);
        return loginSessionRepository.save(updatedSession);
    }

    @Override
    public void delete(Integer id) {
        if (!loginSessionRepository.existsById(id)) {
            throw new IllegalArgumentException("LoginSession with ID " + id + " does not exist.");
        }
        loginSessionRepository.deleteById(id);
    }

    @Override
    public List<LoginSession> findByUserId(Integer userId) {
        List<LoginSessionEntity> sessionEntities = loginSessionRepository.findByUserId(userId);
        return sessionEntities.stream()
                .map(sessionEntity -> modelMapper.map(sessionEntity, LoginSession.class))
                .collect(Collectors.toList());
    }
}