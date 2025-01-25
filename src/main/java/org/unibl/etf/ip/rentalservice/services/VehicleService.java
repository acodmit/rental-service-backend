package org.unibl.etf.ip.rentalservice.services;

import org.unibl.etf.ip.rentalservice.core.CrudService;
import org.unibl.etf.ip.rentalservice.model.dto.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService extends CrudService<Integer> {
    List<Vehicle> getAllAvailableVehicles();
    Vehicle getVehicleDetails(Integer id);
    void updateVehicleStatus(Integer id, String status);
}
