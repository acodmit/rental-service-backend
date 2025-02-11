package org.unibl.etf.ip.rentalservice.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class Rental {
    private Integer id;
    private Timestamp startDate;
    private Timestamp endDate;
    private Integer totalDurationMinutes;
    private BigDecimal totalPrice;
    private Vehicle vehicle;
    private Client client;
    private Location pickUpLocation;
    private Location dropOffLocation;
}
