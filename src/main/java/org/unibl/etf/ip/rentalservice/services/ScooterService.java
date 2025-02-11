package org.unibl.etf.ip.rentalservice.services;

import org.unibl.etf.ip.rentalservice.core.CrudService;
import org.unibl.etf.ip.rentalservice.model.dto.Scooter;

import java.util.List;

public interface ScooterService extends CrudService<Integer> {
    List<Scooter> findByModel(String model);

    List<Scooter> findByMaxSpeedKmhGreaterThanEqual(int maxSpeedKmh);
}
