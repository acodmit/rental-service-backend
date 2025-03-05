package org.unibl.etf.ip.rentalservice.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.ip.rentalservice.core.CrudController;
import org.unibl.etf.ip.rentalservice.model.dto.Scooter;
import org.unibl.etf.ip.rentalservice.model.requests.ScooterRequest;
import org.unibl.etf.ip.rentalservice.services.ScooterService;

@RestController
@RequestMapping("/scooters")
public class ScooterController extends CrudController<Integer, ScooterRequest, Scooter> {

    public ScooterController(final ScooterService scooterService) {
        super(Scooter.class, scooterService);
    }
}