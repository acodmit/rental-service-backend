package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.dto.Manufacturer;

import java.util.List;

public interface ManufacturerEntityRepository extends JpaRepository<Manufacturer, Integer> {
    // Find by name
    Manufacturer findByName(String name);

    // Find by country
    List<Manufacturer> findByCountry(String country);
}
