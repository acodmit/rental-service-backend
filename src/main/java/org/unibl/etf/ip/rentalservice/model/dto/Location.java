package org.unibl.etf.ip.rentalservice.model.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Data
public class Location {
    private Integer id;
    private BigDecimal latitude;
    private BigDecimal longitude;
    //private List<Rental> pickUpRentals;
    //private List<Rental> dropOffRentals;
}
