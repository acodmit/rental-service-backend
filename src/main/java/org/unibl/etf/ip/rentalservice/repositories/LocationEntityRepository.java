package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.dto.Location;
import org.unibl.etf.ip.rentalservice.model.entities.LocationEntity;

import java.math.BigDecimal;
import java.util.Optional;

public interface LocationEntityRepository extends JpaRepository<LocationEntity, Integer> {
    // Find location by latitude and longitude
    Optional<LocationEntity> findByLongitudeAndLatitude(BigDecimal latitude, BigDecimal longitude);
}
