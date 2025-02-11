package org.unibl.etf.ip.rentalservice.services.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.model.dto.Location;
import org.unibl.etf.ip.rentalservice.model.entities.LocationEntity;
import org.unibl.etf.ip.rentalservice.repositories.LocationEntityRepository;
import org.unibl.etf.ip.rentalservice.services.LocationService;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class LocationServiceImpl extends CrudJpaService<LocationEntity, Integer> implements LocationService {

    private final LocationEntityRepository locationEntityRepository;

    public LocationServiceImpl(LocationEntityRepository locationEntityRepository, ModelMapper modelMapper) {
        super(locationEntityRepository, LocationEntity.class, modelMapper);
        this.locationEntityRepository = locationEntityRepository;
    }

    @Override
    public Optional<Location> findByCoordinates(BigDecimal latitude, BigDecimal longitude) {
        Optional<LocationEntity> location = locationEntityRepository.findByLongitudeAndLatitude(latitude, longitude);
        return location.map(loc -> getModelMapper().map(loc, Location.class));
    }
}
