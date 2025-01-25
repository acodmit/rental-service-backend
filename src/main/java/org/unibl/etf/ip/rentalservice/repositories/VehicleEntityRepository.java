package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.dto.Vehicle;
import org.unibl.etf.ip.rentalservice.model.enums.VehicleType;

import java.util.List;

public interface VehicleEntityRepository extends JpaRepository<Vehicle, Integer> {
    // Find all vehicles by type
    List<Vehicle> findByType(VehicleType type);

    // Find all broken vehicles
    List<Vehicle> findByIsBrokenTrue();
}