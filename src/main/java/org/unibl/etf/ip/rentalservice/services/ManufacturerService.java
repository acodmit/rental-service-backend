package org.unibl.etf.ip.rentalservice.services;

import org.unibl.etf.ip.rentalservice.model.dto.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {

    Manufacturer save(Manufacturer manufacturer);

    Optional<Manufacturer> findById(Integer id);

    List<Manufacturer> findAll();

    Manufacturer update(Integer id, Manufacturer updatedManufacturer);

    void delete(Integer id);

    Manufacturer findByName(String name);

    List<Manufacturer> findByCountry(String country);
}
