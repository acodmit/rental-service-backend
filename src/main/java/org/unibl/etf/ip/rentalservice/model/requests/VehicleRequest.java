package org.unibl.etf.ip.rentalservice.model.requests;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.unibl.etf.ip.rentalservice.model.dto.Fault;
import org.unibl.etf.ip.rentalservice.model.dto.Manufacturer;
import org.unibl.etf.ip.rentalservice.model.dto.Rental;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequest {
    private String type;
    private Timestamp acquisitionDate;
    private BigDecimal purchasePrice;
    private Boolean isBroken;
    private String imageUrl;
    private String model;
    private BigDecimal hourlyRate;
    //private List<Fault> faults;
    //private List<Rental> rentals;
    private Integer manufacturerId;
}