package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.dto.Rental;

import java.util.List;

public interface RentalEntityRepository extends JpaRepository<Rental, Integer> {
    // Find rentals by client ID
    List<Rental> findByClientId(Integer clientId);

    // Find rentals by vehicle ID
    List<Rental> findByVehicleId(Integer vehicleId);

    // Find active rentals (end date is null)
    List<Rental> findByEndDateIsNull();
}