package org.unibl.etf.ip.rentalservice.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.unibl.etf.ip.rentalservice.model.dto.Manufacturer;
import org.unibl.etf.ip.rentalservice.repositories.ManufacturerEntityRepository;
import org.unibl.etf.ip.rentalservice.services.ManufacturerService;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.model.entities.ManufacturerEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ManufacturerServiceImpl extends CrudJpaService<ManufacturerEntity, Integer> implements ManufacturerService {

    private final ManufacturerEntityRepository manufacturerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ManufacturerServiceImpl(ManufacturerEntityRepository manufacturerRepository, ModelMapper modelMapper) {
        super(manufacturerRepository, ManufacturerEntity.class, modelMapper);
        this.manufacturerRepository = manufacturerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        ManufacturerEntity entity = modelMapper.map(manufacturer, ManufacturerEntity.class);
        ManufacturerEntity savedEntity = manufacturerRepository.save(entity);
        return modelMapper.map(savedEntity, Manufacturer.class);
    }

    @Override
    public Optional<Manufacturer> findById(Integer id) {
        Optional<ManufacturerEntity> entity = manufacturerRepository.findById(id);
        return entity.map(e -> modelMapper.map(e, Manufacturer.class));
    }

    @Override
    public List<Manufacturer> findAll() {
        List<ManufacturerEntity> entities = manufacturerRepository.findAll();
        return entities.stream()
                .map(e -> modelMapper.map(e, Manufacturer.class))
                .collect(Collectors.toList());
    }

    @Override
    public Manufacturer update(Integer id, Manufacturer updatedManufacturer) {
        if (!manufacturerRepository.existsById(id)) {
            throw new IllegalArgumentException("Manufacturer with ID " + id + " does not exist.");
        }
        ManufacturerEntity entity = modelMapper.map(updatedManufacturer, ManufacturerEntity.class);
        entity.setId(id);
        ManufacturerEntity updatedEntity = manufacturerRepository.save(entity);
        return modelMapper.map(updatedEntity, Manufacturer.class);
    }

    @Override
    public void delete(Integer id) {
        if (!manufacturerRepository.existsById(id)) {
            throw new IllegalArgumentException("Manufacturer with ID " + id + " does not exist.");
        }
        manufacturerRepository.deleteById(id);
    }

    @Override
    public Manufacturer findByName(String name) {
        ManufacturerEntity entity = manufacturerRepository.findByName(name);
        return modelMapper.map(entity, Manufacturer.class);
    }

    @Override
    public List<Manufacturer> findByCountry(String country) {
        List<ManufacturerEntity> entities = manufacturerRepository.findByCountry(country);
        return entities.stream()
                .map(e -> modelMapper.map(e, Manufacturer.class))
                .collect(Collectors.toList());
    }
}

