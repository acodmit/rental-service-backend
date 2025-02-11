package org.unibl.etf.ip.rentalservice.services;

import org.unibl.etf.ip.rentalservice.core.CrudService;
import org.unibl.etf.ip.rentalservice.model.dto.Location;
import org.unibl.etf.ip.rentalservice.model.dto.Rental;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LocationService extends CrudService<Integer> {
    // Find location by coordinates (latitude and longitude)
    Optional<Location> findByCoordinates(BigDecimal latitude, BigDecimal longitude);
}