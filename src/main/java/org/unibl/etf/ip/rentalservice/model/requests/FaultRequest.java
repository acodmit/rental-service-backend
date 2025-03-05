package org.unibl.etf.ip.rentalservice.model.requests;

import lombok.*;

import java.sql.Timestamp;

@Data
public class FaultRequest {
    private String description;
    private Timestamp reportedDate;
    private Integer vehicleId;
}
