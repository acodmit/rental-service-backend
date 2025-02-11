package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.rentalservice.model.dto.Manufacturer;
import org.unibl.etf.ip.rentalservice.model.entities.ManufacturerEntity;

import java.util.List;

public interface ManufacturerEntityRepository extends JpaRepository<ManufacturerEntity, Integer> {
    // Find by name
    ManufacturerEntity findByName(String name);

    // Find by country
    List<ManufacturerEntity> findByCountry(String country);
}
