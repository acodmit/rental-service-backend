package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.dto.Scooter;
import org.unibl.etf.ip.rentalservice.model.entities.ScooterEntity;

import java.util.List;

public interface ScooterEntityRepository extends JpaRepository<ScooterEntity, Integer> {
    // Find scooters by model
    List<ScooterEntity> findByModel(String model);

    // Find scooters by maximum speed
    List<ScooterEntity> findByMaxSpeedKmhGreaterThanEqual(int maxSpeedKmh);
}