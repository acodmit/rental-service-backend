package org.unibl.etf.ip.rentalservice.model.requests;

import lombok.*;
import org.unibl.etf.ip.rentalservice.model.dto.Vehicle;

import java.sql.Timestamp;

@Data
public class FaultRequest {
    private String description;
    private Timestamp reportedDate;
    private Integer vehicleId;
}
