package org.unibl.etf.ip.rentalservice.services;

import org.unibl.etf.ip.rentalservice.core.CrudService;
import org.unibl.etf.ip.rentalservice.model.dto.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService extends CrudService<Integer> {
    // Find manufacturer by name
    Manufacturer findByName(String name);

    // Find manufacturers by country
    List<Manufacturer> findByCountry(String country);

    // Get all manufacturers with associated vehicles
    List<Manufacturer> findAllManufacturersWithVehicles();
}
