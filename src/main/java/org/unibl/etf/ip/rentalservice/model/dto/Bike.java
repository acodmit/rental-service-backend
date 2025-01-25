package org.unibl.etf.ip.rentalservice.model.dto;

import lombok.*;

@Data
public class Bike {
    private Integer id;
    private String model;
    private Integer rangeKm;
    private Vehicle vehicle;

}
