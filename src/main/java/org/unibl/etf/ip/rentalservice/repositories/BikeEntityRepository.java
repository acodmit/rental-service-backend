package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.dto.Bike;
import org.unibl.etf.ip.rentalservice.model.entities.BikeEntity;

import java.util.List;

public interface BikeEntityRepository extends JpaRepository<BikeEntity, Integer> {
    // Find bikes by model
    List<BikeEntity> findByModel(String model);

    // Find bikes by range
    List<BikeEntity> findByRangeKmGreaterThanEqual(int rangeKm);

}