package org.unibl.etf.ip.rentalservice.services.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.rentalservice.core.CrudJpaService;
import org.unibl.etf.ip.rentalservice.exceptions.NotFoundException;
import org.unibl.etf.ip.rentalservice.model.dto.*;
import org.unibl.etf.ip.rentalservice.model.entities.*;
import org.unibl.etf.ip.rentalservice.model.requests.BikeRequest;
import org.unibl.etf.ip.rentalservice.model.requests.CarRequest;
import org.unibl.etf.ip.rentalservice.model.requests.FaultRequest;
import org.unibl.etf.ip.rentalservice.model.requests.ScooterRequest;
import org.unibl.etf.ip.rentalservice.repositories.*;
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
    private final BikeEntityRepository bikeEntityRepository;
    private final CarEntityRepository carEntityRepository;
    private final ScooterEntityRepository scooterEntityRepository;
    private final FaultEntityRepository faultEntityRepository;

    public VehicleServiceImpl(VehicleEntityRepository vehicleEntityRepository, ModelMapper modelMapper,
                              BikeEntityRepository bikeEntityRepository, CarEntityRepository carEntityRepository, ScooterEntityRepository scooterEntityRepository,
                              FaultEntityRepository faultEntityRepository) {
        super(vehicleEntityRepository, VehicleEntity.class, modelMapper);
        this.vehicleEntityRepository = vehicleEntityRepository;
        this.bikeEntityRepository = bikeEntityRepository;
        this.carEntityRepository = carEntityRepository;
        this.scooterEntityRepository = scooterEntityRepository;
        this.faultEntityRepository = faultEntityRepository;
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
        BikeEntity bikeEntity = getModelMapper().map(BikeRequest.builder()
                .acquisitionDate(Timestamp.valueOf(line[1]))
                .purchasePrice(new BigDecimal(line[2]))
                .isBroken(Boolean.parseBoolean(line[3]))
                .imageUrl(line[4])
                .model(line[5])
                .hourlyRate(new BigDecimal(line[6]))
                .manufacturerId(Integer.parseInt(line[7]))
                .rangeKm(Integer.parseInt(line[8]))
                .build(), BikeEntity.class);
        bikeEntity.setId(null);
        bikeEntity = bikeEntityRepository.saveAndFlush(bikeEntity);
        getEntityManager().refresh(bikeEntity);
        return getModelMapper().map(bikeEntity, Bike.class);
    }

    private Car insertCar(String[] line) {
        CarEntity carEntity = getModelMapper().map(CarRequest.builder()
                .acquisitionDate(Timestamp.valueOf(line[1]))
                .purchasePrice(new BigDecimal(line[2]))
                .isBroken(Boolean.parseBoolean(line[3]))
                .imageUrl(line[4])
                .model(line[5])
                .hourlyRate(new BigDecimal(line[6]))
                .manufacturerId(Integer.parseInt(line[7]))
                .description(line[8])
                .build(), CarEntity.class);
        carEntity.setId(null);
        carEntity = carEntityRepository.saveAndFlush(carEntity);
        getEntityManager().refresh(carEntity);
        return getModelMapper().map(carEntity, Car.class);
    }

    private Scooter insertScooter(String[] line) {
        ScooterEntity scooterEntity = getModelMapper().map(ScooterRequest.builder()
                .acquisitionDate(Timestamp.valueOf(line[1]))
                .purchasePrice(new BigDecimal(line[2]))
                .isBroken(Boolean.parseBoolean(line[3]))
                .imageUrl(line[4])
                .model(line[5])
                .hourlyRate(new BigDecimal(line[6]))
                .manufacturerId(Integer.parseInt(line[7]))
                .maxSpeedKmh(Integer.parseInt(line[8]))
                .build(), ScooterEntity.class);
        scooterEntity.setId(null);
        scooterEntity = scooterEntityRepository.saveAndFlush(scooterEntity);
        getEntityManager().refresh(scooterEntity);
        return getModelMapper().map(scooterEntity, Scooter.class);
    }

    @Override
    public Fault addFaultToVehicle(Integer vehicleId, FaultRequest faultRequest) {
        // Check if the vehicle exists
        VehicleEntity vehicleEntity = vehicleEntityRepository.findById(vehicleId)
                .orElseThrow(() -> new NotFoundException("Vehicle with ID " + vehicleId + " not found"));

        // Update isBroken if it's currently false
        if (Boolean.FALSE.equals(vehicleEntity.getIsBroken())) {
            vehicleEntity.setIsBroken(true);
            vehicleEntityRepository.save(vehicleEntity); // Persist change
        }

        // Map the FaultRequest to FaultEntity
        FaultEntity faultEntity = getModelMapper().map(faultRequest, FaultEntity.class);
        faultEntity.setId(null);
        faultEntity.setVehicle(vehicleEntity); // Set the vehicle relationship

        // Save the fault
        FaultEntity savedFault = faultEntityRepository.save(faultEntity);

        // Map to DTO and return
        return getModelMapper().map(savedFault, Fault.class);
    }

    @Override
    public boolean deleteVehicleFault(Integer vehicleId, Integer faultId) {
        // Check if the fault exists
        FaultEntity faultEntity = faultEntityRepository.findById(faultId)
                .orElseThrow(() -> new NotFoundException("Fault with ID " + faultId + " not found"));

        // Check if the fault belongs to the vehicle
        VehicleEntity vehicleEntity = vehicleEntityRepository.findById(vehicleId)
                .orElseThrow(() -> new NotFoundException("Vehicle with ID " + vehicleId + " not found"));

        if (!faultEntity.getVehicle().getId().equals(vehicleId)) {
            return false;
        }

        // Delete the fault
        faultEntityRepository.delete(faultEntity);

        // Check if the vehicle has any remaining faults
        if (faultEntityRepository.findByVehicleId(vehicleId).isEmpty()) {
            vehicleEntity.setIsBroken(false);
            vehicleEntityRepository.save(vehicleEntity); // Persist change
        }

        return true;
    }

}
