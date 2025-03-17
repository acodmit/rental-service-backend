package org.unibl.etf.ip.rentalservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.rentalservice.core.CrudController;
import org.unibl.etf.ip.rentalservice.model.dto.Fault;
import org.unibl.etf.ip.rentalservice.model.requests.FaultRequest;
import org.unibl.etf.ip.rentalservice.services.FaultService;

@RestController
@RequestMapping("/faults")
public class FaultController extends CrudController<Integer, FaultRequest, Fault>{

    private final FaultService faultService;

    public FaultController(final FaultService faultService) {
        super(Fault.class, faultService);
        this.faultService = faultService;
    }

    @Override
    @PostMapping
    public ResponseEntity<Fault> insert(@RequestBody FaultRequest faultRequest) {
        Fault fault = faultService.addFault(faultRequest);
        return new ResponseEntity<>(fault, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean deleted = faultService.deleteFault(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}