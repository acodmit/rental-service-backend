package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.entities.VehicleEntity;
import org.unibl.etf.ip.rentalservice.model.enums.VehicleType;

import java.util.List;

public interface VehicleEntityRepository extends JpaRepository<VehicleEntity, Integer> {
    // Find all vehicles by type
    // List<VehicleEntity> findByType(VehicleType type);

    // Find all broken vehicles
    List<VehicleEntity> findByIsBrokenTrue();
}