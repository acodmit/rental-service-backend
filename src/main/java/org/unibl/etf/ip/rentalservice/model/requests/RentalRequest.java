package org.unibl.etf.ip.rentalservice.model.requests;

import lombok.Data;
import org.unibl.etf.ip.rentalservice.model.dto.Location;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class RentalRequest {
    private Timestamp startDate;
    private Timestamp endDate;
    private Integer totalDurationMinutes;
    private BigDecimal totalPrice;
    private Integer vehicleId;
    private Integer clientId;
    private Location pickUpLocation;
    private Location dropOffLocation;
}
