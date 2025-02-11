package org.unibl.etf.ip.rentalservice.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.model.dto.Scooter;
import org.unibl.etf.ip.rentalservice.model.entities.ScooterEntity;
import org.unibl.etf.ip.rentalservice.repositories.ScooterEntityRepository;
import org.unibl.etf.ip.rentalservice.services.ScooterService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScooterServiceImpl extends CrudJpaService<ScooterEntity, Integer> implements ScooterService {

    private final ScooterEntityRepository scooterEntityRepository;

    public ScooterServiceImpl(ScooterEntityRepository scooterEntityRepository, ModelMapper modelMapper) {
        super(scooterEntityRepository, ScooterEntity.class, modelMapper);
        this.scooterEntityRepository = scooterEntityRepository;
    }

    @Override
    public List<Scooter> findByModel(String model) {
        List<ScooterEntity> scooterEntities = scooterEntityRepository.findByModel(model);
        return scooterEntities.stream()
                .map(scooterEntity -> getModelMapper().map(scooterEntity, Scooter.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Scooter> findByMaxSpeedKmhGreaterThanEqual(int maxSpeedKmh) {
        List<ScooterEntity> scooterEntities = scooterEntityRepository.findByMaxSpeedKmhGreaterThanEqual(maxSpeedKmh);
        return scooterEntities.stream()
                .map(scooterEntity -> getModelMapper().map(scooterEntity, Scooter.class))
                .collect(Collectors.toList());
    }
}
