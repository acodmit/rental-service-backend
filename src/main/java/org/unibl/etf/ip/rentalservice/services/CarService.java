package org.unibl.etf.ip.rentalservice.services;

import org.unibl.etf.ip.rentalservice.core.CrudService;
import org.unibl.etf.ip.rentalservice.model.dto.Car;

import java.util.List;

public interface CarService extends CrudService<Integer> {
    List<Car> getCarsByModel(String model);
}
