package org.unibl.etf.ip.rentalservice.services;

import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.rentalservice.core.CrudService;
import org.unibl.etf.ip.rentalservice.model.dto.Fault;
import org.unibl.etf.ip.rentalservice.model.dto.Vehicle;
import org.unibl.etf.ip.rentalservice.model.requests.FaultRequest;

import java.math.BigDecimal;
import java.util.List;

public interface VehicleService extends CrudService<Integer> {
    // Find all vehicles by type
    // List<Vehicle> findByType(VehicleType type);

    // Find all broken vehicles
    List<Vehicle> findBrokenVehicles();

    // Upload vehicles from csv
    List<? extends Vehicle> uploadCsv(MultipartFile file);

    // Add a failure to a vehicle
    Fault addFaultToVehicle(Integer vehicleId, FaultRequest faultRequest);

    // Delete a failure from a vehicle
    boolean deleteVehicleFault(Integer vehicleId, Integer faultId);

    // Set new hourly rate for a vehicle
    Vehicle setHourlyRate(Integer vehicleId, BigDecimal hourlyRate);
}
