package org.unibl.etf.ip.rentalservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.ip.rentalservice.core.CrudController;
import org.unibl.etf.ip.rentalservice.model.dto.Bike;
import org.unibl.etf.ip.rentalservice.model.requests.BikeRequest;
import org.unibl.etf.ip.rentalservice.services.BikeService;

@RestController
@RequestMapping("/bikes")
public class BikeController extends CrudController<Integer, BikeRequest, Bike> {

    public BikeController(final BikeService bikeService) {
        super(Bike.class, bikeService);
    }
}
