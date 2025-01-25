package org.unibl.etf.ip.rentalservice.model.dto;

import lombok.*;

@Data
public class Scooter {
    private Integer id;
    private String model;
    private Integer maxSpeedKmh;
    private Vehicle vehicle;
}
