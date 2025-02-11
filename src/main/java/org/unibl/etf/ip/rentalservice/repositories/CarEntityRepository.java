package org.unibl.etf.ip.rentalservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unibl.etf.ip.rentalservice.model.dto.Car;
import org.unibl.etf.ip.rentalservice.model.entities.CarEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarEntityRepository extends JpaRepository<CarEntity, Integer> {

    // Find all cars by model
    List<CarEntity> findByModel(String model);
}
