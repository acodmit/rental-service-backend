package org.unibl.etf.ip.rentalservice.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
public class Vehicle {
    private Integer id;
    private Date acquisitionDate;
    private BigDecimal purchasePrice;
    private Boolean isBroken;
    private String imageUrl;
    private String model;
    private Manufacturer manufacturer;
    //private List<Fault> faults;
    //private List<Rental> rentals;
    private BigDecimal hourlyRate;
}