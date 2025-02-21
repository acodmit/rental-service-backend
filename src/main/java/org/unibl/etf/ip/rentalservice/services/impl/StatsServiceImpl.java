package org.unibl.etf.ip.rentalservice.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.rentalservice.model.dto.RevenueByDay;
import org.unibl.etf.ip.rentalservice.model.dto.RevenueByVehicleType;
import org.unibl.etf.ip.rentalservice.model.dto.Vehicle;
import org.unibl.etf.ip.rentalservice.model.entities.RentalEntity;
import org.unibl.etf.ip.rentalservice.model.entities.VehicleEntity;
import org.unibl.etf.ip.rentalservice.repositories.*;
import org.unibl.etf.ip.rentalservice.services.StatsService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatsServiceImpl implements StatsService {

    private final RentalEntityRepository rentalEntityRepository;
    private final BikeEntityRepository bikeEntityRepository;
    private final CarEntityRepository carEntityRepository;
    private final ScooterEntityRepository scooterEntityRepository;
    private final VehicleEntityRepository vehicleEntityRepository;

    private final ModelMapper modelMapper;

    public StatsServiceImpl(RentalEntityRepository rentalEntityRepository,
                            BikeEntityRepository bikeEntityRepository,
                            CarEntityRepository carEntityRepository,
                            ScooterEntityRepository scooterEntityRepository,
                            VehicleEntityRepository vehicleEntityRepository,
                            ModelMapper modelMapper) {
        this.rentalEntityRepository = rentalEntityRepository;
        this.bikeEntityRepository = bikeEntityRepository;
        this.carEntityRepository = carEntityRepository;
        this.scooterEntityRepository = scooterEntityRepository;
        this.vehicleEntityRepository = vehicleEntityRepository;
        this.modelMapper = modelMapper;
    }

    // Get total revenue by day for a given month
    public List<RevenueByDay> getRevenueByMonth(int month) {
        // Calculate the start and end of the month
        Timestamp startOfMonth = getStartOfMonth(month);
        Timestamp endOfMonth = getEndOfMonth(month);

        // Fetch all rentals within the given month
        List<RentalEntity> rentals = rentalEntityRepository.findByStartDateBetweenOrderByStartDate(startOfMonth, endOfMonth);

        // Group and aggregate by day (revenue calculation)
        Map<String, BigDecimal> revenueByDay = rentals.stream()
                .collect(Collectors.groupingBy(
                        rental -> getDayString(rental.getStartDate()),
                        Collectors.reducing(BigDecimal.ZERO, RentalEntity::getTotalPrice, BigDecimal::add)
                ));

        // Convert and return
        return revenueByDay.entrySet().stream()
                .map(entry -> new RevenueByDay(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public RevenueByVehicleType getRevenueByBike() {
        return getRevenueByVehicle(bikeEntityRepository, "Bike");
    }

    public RevenueByVehicleType getRevenueByCar() {
        return getRevenueByVehicle(carEntityRepository, "Car");
    }

    public RevenueByVehicleType getRevenueByScooter() {
        return getRevenueByVehicle(scooterEntityRepository, "Scooter");
    }

    private <T extends VehicleEntity> RevenueByVehicleType getRevenueByVehicle(
            JpaRepository<T, Integer> vehicleRepository, String vehicleType) {

        List<T> vehicles = vehicleRepository.findAll();

        List<RentalEntity> rentals = vehicles.stream()
                .flatMap(vehicle -> rentalEntityRepository.findByVehicleId(vehicle.getId()).stream())
                .toList();

        // Calculate total revenue
        BigDecimal totalRevenue = rentals.stream()
                .map(RentalEntity::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate revenue per day
        List<RevenueByDay> revenueByDay = rentals.stream()
                .collect(Collectors.groupingBy(
                        rental -> rental.getStartDate().toLocalDateTime().toLocalDate(),
                        Collectors.reducing(BigDecimal.ZERO, RentalEntity::getTotalPrice, BigDecimal::add)
                ))
                .entrySet().stream()
                .map(entry -> new RevenueByDay(entry.getKey().toString(), entry.getValue()))
                .toList();

        return new RevenueByVehicleType(vehicleType, totalRevenue, revenueByDay);
    }

    // Get faults number by each vehicle
    @Override
    public Map<Vehicle, Integer> getFaultsByVehicle() {
        return vehicleEntityRepository.findAll().stream()
                .collect(Collectors.toMap(
                        vehicle -> modelMapper.map(vehicle, Vehicle.class),
                        vehicle -> vehicle.getFaults().size() // Assuming getFaults() returns a List
                ));
    }


    private Timestamp getStartOfMonth(int month) {
        // Assuming we want the current year
        int currentYear = LocalDate.now().getYear();

        // Get the first day of the month
        LocalDate startOfMonth = LocalDate.of(currentYear, Month.of(month), 1);

        // Convert to Timestamp
        LocalDateTime startOfMonthDateTime = startOfMonth.atStartOfDay();
        return Timestamp.valueOf(startOfMonthDateTime);
    }

    private Timestamp getEndOfMonth(int month) {
        // Assuming we want the current year
        int currentYear = LocalDate.now().getYear();

        // Get the last day of the month
        LocalDate endOfMonth = LocalDate.of(currentYear, Month.of(month), 1)
                .withDayOfMonth(LocalDate.of(currentYear, Month.of(month), 1)
                        .lengthOfMonth());

        // Convert to Timestamp
        LocalDateTime endOfMonthDateTime = endOfMonth.atTime(23, 59, 59, 999999999); // End of day
        return Timestamp.valueOf(endOfMonthDateTime);
    }

    private String getDayString(Timestamp timestamp) {
        // Convert timestamp to a day string (e.g., "2025-02-01")
        return new SimpleDateFormat("yyyy-MM-dd").format(timestamp);
    }
}

