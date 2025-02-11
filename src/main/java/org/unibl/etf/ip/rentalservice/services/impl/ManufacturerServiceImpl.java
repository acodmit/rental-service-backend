package org.unibl.etf.ip.rentalservice.services.impl;

import org.springframework.stereotype.Service;
import org.unibl.etf.ip.rentalservice.model.dto.Manufacturer;
import org.unibl.etf.ip.rentalservice.repositories.ManufacturerEntityRepository;
import org.unibl.etf.ip.rentalservice.services.ManufacturerService;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.model.entities.ManufacturerEntity;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ManufacturerServiceImpl extends CrudJpaService<ManufacturerEntity, Integer> implements ManufacturerService {

    private final ManufacturerEntityRepository manufacturerEntityRepository;

    public ManufacturerServiceImpl(ManufacturerEntityRepository manufacturerEntityRepository, ModelMapper modelMapper) {
        super(manufacturerEntityRepository, ManufacturerEntity.class, modelMapper);
        this.manufacturerEntityRepository = manufacturerEntityRepository;
    }

    @Override
    public Manufacturer findByName(String name) {
        ManufacturerEntity manufacturer = manufacturerEntityRepository.findByName(name);
        return getModelMapper().map(manufacturer, Manufacturer.class);
    }

    @Override
    public List<Manufacturer> findByCountry(String country) {
        List<ManufacturerEntity> manufacturers = manufacturerEntityRepository.findByCountry(country);
        return manufacturers.stream()
                .map(manufacturer -> getModelMapper().map(manufacturer, Manufacturer.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Manufacturer> findAllManufacturersWithVehicles() {
        List<ManufacturerEntity> manufacturers = manufacturerEntityRepository.findAll();
        return manufacturers.stream()
                .map(manufacturer -> getModelMapper().map(manufacturer, Manufacturer.class))
                .collect(Collectors.toList());
    }
}
