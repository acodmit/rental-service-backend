package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.dto.Location;

import java.math.BigDecimal;

public interface LocationEntityRepository extends JpaRepository<Location, Integer> {
    // Find location by latitude and longitude
    Location findByLongitudeAndLatitude(BigDecimal latitude, BigDecimal longitude);
}
