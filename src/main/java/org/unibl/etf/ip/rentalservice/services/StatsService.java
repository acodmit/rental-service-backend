package org.unibl.etf.ip.rentalservice.services;

import org.springframework.stereotype.Service;
import org.unibl.etf.ip.rentalservice.model.dto.RevenueByDay;
import org.unibl.etf.ip.rentalservice.model.dto.RevenueByVehicleType;
import org.unibl.etf.ip.rentalservice.model.dto.Vehicle;
import org.unibl.etf.ip.rentalservice.model.entities.BikeEntity;
import org.unibl.etf.ip.rentalservice.model.entities.CarEntity;
import org.unibl.etf.ip.rentalservice.model.entities.RentalEntity;
import org.unibl.etf.ip.rentalservice.repositories.BikeEntityRepository;
import org.unibl.etf.ip.rentalservice.repositories.CarEntityRepository;
import org.unibl.etf.ip.rentalservice.repositories.RentalEntityRepository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public interface StatsService {

    // Get total revenue by day for a given month
    List<RevenueByDay> getRevenueByMonth(int month);

    // Get total revenue by bike
    RevenueByVehicleType getRevenueByBike();

    // Get total revenue by car
    RevenueByVehicleType getRevenueByCar();

    // Get total revenue by scooter
    RevenueByVehicleType getRevenueByScooter();

    // Get faults number by each vehicle
    Map<Vehicle, Integer> getFaultsByVehicle();
}
