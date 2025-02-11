package org.unibl.etf.ip.rentalservice.services.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.model.dto.Car;
import org.unibl.etf.ip.rentalservice.model.entities.CarEntity;
import org.unibl.etf.ip.rentalservice.repositories.CarEntityRepository;
import org.unibl.etf.ip.rentalservice.services.CarService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarServiceImpl extends CrudJpaService<CarEntity, Integer> implements CarService {

    private final CarEntityRepository carEntityRepository;

    public CarServiceImpl(CarEntityRepository carEntityRepository, ModelMapper modelMapper) {
        super(carEntityRepository, CarEntity.class, modelMapper);
        this.carEntityRepository = carEntityRepository;
    }

    @Override
    public List<Car> getCarsByModel(String model) {
        List<CarEntity> carEntities = carEntityRepository.findByModel(model);
        return carEntities.stream()
                .map(car -> getModelMapper().map(car, Car.class))
                .collect(Collectors.toList());
    }
}