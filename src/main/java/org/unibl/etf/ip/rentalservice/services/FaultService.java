package org.unibl.etf.ip.rentalservice.services;

import org.unibl.etf.ip.rentalservice.core.CrudService;
import org.unibl.etf.ip.rentalservice.model.dto.Fault;
import org.unibl.etf.ip.rentalservice.model.requests.FaultRequest;

import java.sql.Timestamp;
import java.util.List;

public interface FaultService extends CrudService<Integer> {
    // Find all faults for a specific vehicle
    List<Fault> findByVehicleId(Integer vehicleId);

    // Find all faults reported within a date range
    // List<Fault> findByReportedDateBetween(Timestamp startDate, Timestamp endDate);

    // Add a new fault
    Fault addFault(FaultRequest faultRequest);

    // Delete a fault
    boolean deleteFault(Integer faultId);
}
