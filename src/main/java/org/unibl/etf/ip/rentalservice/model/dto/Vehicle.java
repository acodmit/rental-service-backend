package org.unibl.etf.ip.rentalservice.model.dto;

import lombok.*;
import org.unibl.etf.ip.rentalservice.model.entities.*;
import org.unibl.etf.ip.rentalservice.model.enums.VehicleType;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
public class Vehicle {
    private Integer id;
    private VehicleType type;
    private Date acquisitionDate;
    private BigDecimal purchasePrice;
    private String description;
    private Boolean isBroken;
    private String imageUrl;
    private BikeEntity bike;
    private CarEntity car;
    private List<FaultEntity> faults;
    private List<RentalEntity> rentals;
    private ScooterEntity scooter;
    private ManufacturerEntity manufacturer;

}
