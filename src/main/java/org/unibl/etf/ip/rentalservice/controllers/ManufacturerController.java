package org.unibl.etf.ip.rentalservice.controllers;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.rentalservice.core.CrudController;
import org.unibl.etf.ip.rentalservice.model.dto.Manufacturer;
import org.unibl.etf.ip.rentalservice.model.requests.ManufacturerRequest;
import org.unibl.etf.ip.rentalservice.services.ManufacturerService;

@RestController
@RequestMapping("/manufacturers")
public class ManufacturerController extends CrudController<Integer, ManufacturerRequest, Manufacturer> {

    public ManufacturerController(ManufacturerService manufacturerService) {
        super(Manufacturer.class, manufacturerService);
    }

}