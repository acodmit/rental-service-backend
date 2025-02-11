package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.dto.Fault;
import org.unibl.etf.ip.rentalservice.model.entities.FaultEntity;

import java.util.List;

public interface FaultEntityRepository extends JpaRepository<FaultEntity, Integer> {
    // Find faults by vehicle ID
    List<FaultEntity> findByVehicleId(Integer vehicleId);

    // Find faults reported after a specific date
    List<FaultEntity> findByReportedDateAfter(java.util.Date date);
}
