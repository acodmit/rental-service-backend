package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.dto.Scooter;

import java.util.List;

public interface ScooterEntityRepository extends JpaRepository<Scooter, Integer> {
    // Find scooters by model
    List<Scooter> findByModel(String model);

    // Find scooters by maximum speed
    List<Scooter> findByMaxSpeedKmhGreaterThanEqual(int maxSpeedKmh);
}