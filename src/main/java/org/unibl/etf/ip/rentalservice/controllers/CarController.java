package org.unibl.etf.ip.rentalservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.ip.rentalservice.core.CrudController;
import org.unibl.etf.ip.rentalservice.model.dto.Car;
import org.unibl.etf.ip.rentalservice.model.requests.CarRequest;
import org.unibl.etf.ip.rentalservice.services.CarService;

@RestController
@RequestMapping("/cars")
public class CarController extends CrudController<Integer, CarRequest, Car> {

    public CarController(final CarService carService) {
        super(Car.class, carService);
    }
}