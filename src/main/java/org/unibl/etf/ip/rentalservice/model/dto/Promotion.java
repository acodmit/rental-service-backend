package org.unibl.etf.ip.rentalservice.model.dto;

import lombok.*;
import java.sql.Date;

@Data
public class Promotion {
    private Integer id;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    //private Employee employee;

}
