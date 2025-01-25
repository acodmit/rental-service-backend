package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.dto.Bike;

import java.util.List;

public interface BikeEntityRepository extends JpaRepository<Bike, Integer> {
    // Find bikes by model
    List<Bike> findByModel(String model);

    // Find bikes by range
    List<Bike> findByRangeKmGreaterThanEqual(int rangeKm);
}