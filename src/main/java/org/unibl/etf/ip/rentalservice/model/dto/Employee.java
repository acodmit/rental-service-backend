package org.unibl.etf.ip.rentalservice.model.dto;

import lombok.*;
import org.unibl.etf.ip.rentalservice.model.enums.UserType;
import java.util.List;

@Data
public class Employee {
    private Integer id;
    private UserType role;
    private User user;
    private List<Promotion> promotions;

}
