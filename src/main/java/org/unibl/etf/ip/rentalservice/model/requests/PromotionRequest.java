package org.unibl.etf.ip.rentalservice.model.requests;

import lombok.*;
import org.unibl.etf.ip.rentalservice.model.dto.Employee;

import java.sql.Timestamp;

@Data
public class PromotionRequest {
    private String title;
    private String description;
    private Timestamp startDate;
    private Timestamp endDate;
    private Integer employeeId;

}
