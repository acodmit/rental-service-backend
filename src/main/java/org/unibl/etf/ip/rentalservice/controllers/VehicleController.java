package org.unibl.etf.ip.rentalservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.unibl.etf.ip.rentalservice.core.CrudController;
import org.unibl.etf.ip.rentalservice.model.dto.Fault;
import org.unibl.etf.ip.rentalservice.model.dto.Rental;
import org.unibl.etf.ip.rentalservice.model.dto.Vehicle;
import org.unibl.etf.ip.rentalservice.model.requests.FaultRequest;
import org.unibl.etf.ip.rentalservice.model.requests.VehicleRequest;
import org.unibl.etf.ip.rentalservice.services.FaultService;
import org.unibl.etf.ip.rentalservice.services.RentalService;
import org.unibl.etf.ip.rentalservice.services.VehicleService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vehicles")
public class VehicleController extends CrudController<Integer, VehicleRequest, Vehicle> {

    private final VehicleService vehicleService;
    private final RentalService rentalService;
    private final FaultService faultService;

    public VehicleController(VehicleService vehicleService, RentalService rentalService, FaultService faultService) {
        super(Vehicle.class, vehicleService);
        this.vehicleService = vehicleService;
        this.rentalService = rentalService;
        this.faultService = faultService;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<Vehicle> insert(@RequestBody VehicleRequest vehicleRequest) {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<Vehicle> update(@PathVariable Integer id, @RequestBody VehicleRequest vehicleRequest) {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    // Additional custom methods for specific routes (like CSV upload, rentals, failures)

    @PostMapping("/upload-csv")
    public ResponseEntity<List<? extends Vehicle>> uploadCsv(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(vehicleService.uploadCsv(file), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/rentals")
    public ResponseEntity<List<Rental>> findAllRentalsByVehicle(@PathVariable Integer id) {
        List<Rental> rentals = rentalService.findRentalsByVehicleId(id);
        return rentals != null ? new ResponseEntity<>(rentals, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/faults")
    public ResponseEntity<List<Fault>> findAllFaultsByVehicle(@PathVariable Integer id) {
        List<Fault> faults = faultService.findByVehicleId(id);
        return faults != null ? new ResponseEntity<>(faults, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}/faults")
    public ResponseEntity<Fault> addFaultToVehicle(@PathVariable Integer id, @RequestBody FaultRequest faultRequest) {
        Fault fault = vehicleService.addFaultToVehicle(id, faultRequest);
        return new ResponseEntity<>(fault, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/faults/{failureId}")
    public ResponseEntity<Void> deleteVehicleFault(@PathVariable Integer id, @PathVariable Integer failureId) {
        boolean deleted = vehicleService.deleteVehicleFault(id, failureId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Update the hourly rate of a vehicle
    @PatchMapping("/{id}/hourly-rate")
    public ResponseEntity<Vehicle> updateHourlyRate(
            @PathVariable Integer id,
            @RequestBody Map<String, BigDecimal> updateRequest) {
        BigDecimal newHourlyRate = updateRequest.get("hourlyRate");
        Vehicle updatedVehicle = vehicleService.setHourlyRate(id, newHourlyRate);
        return ResponseEntity.ok(updatedVehicle);
    }
}