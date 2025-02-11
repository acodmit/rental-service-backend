package org.unibl.etf.ip.rentalservice.services.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.model.dto.Bike;
import org.unibl.etf.ip.rentalservice.model.entities.BikeEntity;
import org.unibl.etf.ip.rentalservice.repositories.BikeEntityRepository;
import org.unibl.etf.ip.rentalservice.services.BikeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BikeServiceImpl extends CrudJpaService<BikeEntity, Integer> implements BikeService {
    private final BikeEntityRepository bikeEntityRepository;

    public BikeServiceImpl(BikeEntityRepository bikeEntityRepository, ModelMapper modelMapper) {
        super(bikeEntityRepository, BikeEntity.class, modelMapper);
        this.bikeEntityRepository = bikeEntityRepository;
    }

    @Override
    public List<Bike> getBikesByModel(String model) {
        List<BikeEntity> bikeEntities = bikeEntityRepository.findByModel(model);
        return bikeEntities.stream()
                .map(bikeEntity -> getModelMapper().map(bikeEntity, Bike.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Bike> getBikesByRange(int minRangeKm) {
        List<BikeEntity> bikeEntities = bikeEntityRepository.findByRangeKmGreaterThanEqual(minRangeKm);
        return bikeEntities.stream()
                .map(bikeEntity -> getModelMapper().map(bikeEntity, Bike.class))
                .collect(Collectors.toList());
    }
}
