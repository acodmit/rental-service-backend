package org.unibl.etf.ip.rentalservice.services.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.exceptions.NotFoundException;
import org.unibl.etf.ip.rentalservice.model.dto.*;
import org.unibl.etf.ip.rentalservice.model.entities.VehicleEntity;
import org.unibl.etf.ip.rentalservice.model.requests.BikeRequest;
import org.unibl.etf.ip.rentalservice.model.requests.CarRequest;
import org.unibl.etf.ip.rentalservice.model.requests.FaultRequest;
import org.unibl.etf.ip.rentalservice.model.requests.ScooterRequest;
import org.unibl.etf.ip.rentalservice.repositories.VehicleEntityRepository;
import org.unibl.etf.ip.rentalservice.services.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VehicleServiceImpl extends CrudJpaService<VehicleEntity, Integer> implements VehicleService {

    private final VehicleEntityRepository vehicleEntityRepository;

    private final ManufacturerService manufacturerService;
    private final BikeService bikeService;
    private final CarService carService;
    private final ScooterService scooterService;
    private final FaultService faultService;

    public VehicleServiceImpl(VehicleEntityRepository vehicleEntityRepository, ModelMapper modelMapper,
                              ManufacturerService manufacturerService,
                              BikeService bikeService, CarService carService, ScooterService scooterService,
                              FaultService faultService) {
        super(vehicleEntityRepository, VehicleEntity.class, modelMapper);
        this.vehicleEntityRepository = vehicleEntityRepository;
        this.manufacturerService = manufacturerService;
        this.bikeService = bikeService;
        this.carService = carService;
        this.scooterService = scooterService;
        this.faultService = faultService;
    }

    @Override
    public List<Vehicle> findBrokenVehicles() {
        List<VehicleEntity> brokenVehicles = vehicleEntityRepository.findByIsBrokenTrue();
        return brokenVehicles.stream()
                .map(vehicle -> getModelMapper().map(vehicle, Vehicle.class))
                .collect(Collectors.toList());
    }

    // Handling CSV upload
    public List<? extends Vehicle> uploadCsv(MultipartFile file) {
        try {
            List<String[]> lines = readCsv(file);
            return parseRequests(lines);
        } catch (IOException | CsvException e) {
            throw new RuntimeException("Error processing CSV file", e);
        }
    }

    // Reads the CSV file and returns its lines
    private List<String[]> readCsv(MultipartFile file) throws IOException, CsvException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVReader csvReader = new CSVReader(reader)) {
            return csvReader.readAll();
        }
    }

    // Parses CSV lines, creates vehicles, inserts them, and returns the inserted vehicles
    private List<? extends Vehicle> parseRequests(List<String[]> lines) {
        List<Vehicle> insertedVehicles = new ArrayList<>();
        for (String[] line : lines) {
            Vehicle vehicle = generateVehicle(line);
            insertedVehicles.add(vehicle);
        }
        return insertedVehicles;
    }

    // Generates a Vehicle object from a CSV line
    private Vehicle generateVehicle(String[] line) {
        String type = line[0];

        return switch (type) {
            case "Bike" -> insertBike(line);
            case "Car" -> insertCar(line);
            case "Scooter" -> insertScooter(line);
            default -> throw new IllegalArgumentException("Unknown vehicle type: " + type);
        };
    }

    private Bike insertBike(String[] line) {
        return bikeService.insert((BikeRequest.builder()
                .acquisitionDate(Timestamp.valueOf(line[1]))
                .purchasePrice(new BigDecimal(line[2]))
                .isBroken(Boolean.parseBoolean(line[3]))
                .imageUrl(line[4])
                .model(line[5])
                .hourlyRate(new BigDecimal(line[6]))
                .manufacturerId(Integer.parseInt(line[7]))
                .rangeKm(Integer.parseInt(line[8]))
                .build()), Bike.class);
    }

    private Car insertCar(String[] line) {
        return carService.insert((CarRequest.builder()
                .acquisitionDate(Timestamp.valueOf(line[1]))
                .purchasePrice(new BigDecimal(line[2]))
                .isBroken(Boolean.parseBoolean(line[3]))
                .imageUrl(line[4])
                .model(line[5])
                .hourlyRate(new BigDecimal(line[6]))
                .manufacturerId(Integer.parseInt(line[7]))
                .description(line[8])
                .build()), Car.class);
    }

    private Scooter insertScooter(String[] line) {
        return scooterService.insert((ScooterRequest.builder()
                .acquisitionDate(Timestamp.valueOf(line[1]))
                .purchasePrice(new BigDecimal(line[2]))
                .isBroken(Boolean.parseBoolean(line[3]))
                .imageUrl(line[4])
                .model(line[5])
                .hourlyRate(new BigDecimal(line[6]))
                .manufacturerId(Integer.parseInt(line[7]))
                .maxSpeedKmh(Integer.parseInt(line[8]))
                .build()), Scooter.class);
    }

    public Vehicle addFailureToVehicle(Integer vehicleId, FaultRequest faultRequest) {
        // Find the vehicle
        Vehicle vehicle = findById(vehicleId, Vehicle.class);
        if (vehicle == null) {
            throw new NotFoundException("Vehicle with ID " + vehicleId + " not found");
        }
        // Create the fault
        faultService.insert(faultRequest, Fault.class);

        return vehicle;
    }

    public boolean deleteVehicleFault(Integer vehicleId, Integer faultId) {
        // Find the fault
        Fault fault = faultService.findById(faultId, Fault.class);
        if (fault == null) {
            throw new EntityNotFoundException("Fault with ID " + faultId + " not found");
        }

        // Check if the fault belongs to the vehicle
        if (!findById(vehicleId, Vehicle.class).getFaults().contains(fault)) {
            return false;
        }

        // Delete the fault
        faultService.delete(faultId);
        return true;
    }

    /*public void uploadCsv(MultipartFile file) {
        List<Vehicle> vehicles = csvParserService.parseVehicles(file);
        vehicleRepository.saveAll(vehicles);
    }

    public List<Rental> getRentals(Integer vehicleId) {
        return rentalRepository.findByVehicleId(vehicleId);
    }

    public List<Fault> getFailures(Integer vehicleId) {
        return faultRepository.findByVehicleId(vehicleId);
    }


    public boolean deleteFailure(Integer vehicleId, Integer failureId) {
        Optional<Fault> fault = faultRepository.findById(failureId);
        if (fault.isPresent() && fault.get().getVehicle().getId().equals(vehicleId)) {
            faultRepository.deleteById(failureId);
            return true;
        }
        return false;
    }*/
}
