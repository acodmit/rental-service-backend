package org.unibl.etf.ip.rentalservice.model.dto;

import lombok.*;
import java.sql.Timestamp;

@Data
public class Fault {
    private Integer id;
    private String description;
    private Timestamp reportedDate;
    private Vehicle vehicle;
}
