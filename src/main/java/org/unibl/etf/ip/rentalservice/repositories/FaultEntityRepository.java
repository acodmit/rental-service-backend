package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.dto.Fault;

import java.util.List;

public interface FaultEntityRepository extends JpaRepository<Fault, Integer> {
    // Find faults by vehicle ID
    List<Fault> findByVehicleId(Integer vehicleId);

    // Find faults reported after a specific date
    List<Fault> findByReportedDateAfter(java.util.Date date);
}
