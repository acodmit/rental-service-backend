package org.unibl.etf.ip.rentalservice.model.requests;


import lombok.*;
import org.unibl.etf.ip.rentalservice.model.dto.Rental;

import java.math.BigDecimal;
import java.util.List;

@Data
public class LocationRequest {
    private String name;
    private BigDecimal latitude;
    private BigDecimal longitude;
    //private List<Rental> pickUpRentals;
    //private List<Rental> dropOffRentals;
}
