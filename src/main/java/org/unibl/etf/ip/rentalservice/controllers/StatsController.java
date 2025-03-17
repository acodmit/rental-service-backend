package org.unibl.etf.ip.rentalservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.rentalservice.model.dto.RevenueByDay;
import org.unibl.etf.ip.rentalservice.model.dto.RevenueByVehicleType;
import org.unibl.etf.ip.rentalservice.model.dto.Vehicle;
import org.unibl.etf.ip.rentalservice.services.StatsService;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/stats")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    // Endpoint to get revenue for a specific month (grouped by day)
    @GetMapping("/revenue/{yearMonth}")
    public ResponseEntity<List<RevenueByDay>> getRevenueByDay(@PathVariable String yearMonth) {
        List<RevenueByDay> revenueData = statsService.getRevenueByMonth(yearMonth);
        return new ResponseEntity<>(revenueData, HttpStatus.OK);
    }

    // Endpoint to get failure count by vehicle
    @GetMapping("/faults")
    public ResponseEntity<Map<String, Integer>> getFaultsByVehicle() {
        Map<String, Integer> faultsByVehicle = statsService.getFaultsByVehicle();
        return new ResponseEntity<>(faultsByVehicle, HttpStatus.OK);
    }

    // Endpoint to get revenue by vehicle type
    @GetMapping("/revenue-by-type")
    public ResponseEntity<List<RevenueByVehicleType>> getRevenueByType() {
        List<RevenueByVehicleType> revenueByTypeData = Stream.of(
                statsService.getRevenueByBike(),
                statsService.getRevenueByCar(),
                statsService.getRevenueByScooter()
        ).toList();

        return new ResponseEntity<>(revenueByTypeData, HttpStatus.OK);
    }

}

