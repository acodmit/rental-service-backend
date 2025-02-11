package org.unibl.etf.ip.rentalservice.services;

import org.unibl.etf.ip.rentalservice.core.CrudService;
import org.unibl.etf.ip.rentalservice.model.dto.Bike;
import org.unibl.etf.ip.rentalservice.model.entities.BikeEntity;

import java.util.List;

public interface BikeService extends CrudService<Integer> {
    List<Bike> getBikesByModel(String model);
    List<Bike> getBikesByRange(int minRangeKm);
}
