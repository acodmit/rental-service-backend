package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.dto.Rental;
import org.unibl.etf.ip.rentalservice.model.entities.RentalEntity;

import java.sql.Timestamp;
import java.util.List;

public interface RentalEntityRepository extends JpaRepository<RentalEntity, Integer> {
    // Find rentals by client ID
    List<RentalEntity> findByClientId(Integer clientId);

    // Find rentals by vehicle ID
    List<RentalEntity> findByVehicleId(Integer vehicleId);

    // Find active rentals (end date is null)
    List<RentalEntity> findByEndDateIsNull();

    // Revenue by month, grouped by day (using derived queries)
    List<RentalEntity> findByStartDateBetweenOrderByStartDate(Timestamp startDate, Timestamp endDate);

}