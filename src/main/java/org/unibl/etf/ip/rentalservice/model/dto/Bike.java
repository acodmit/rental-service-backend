package org.unibl.etf.ip.rentalservice.model.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class Bike extends Vehicle {
    private Integer id;
    private Integer rangeKm;

}