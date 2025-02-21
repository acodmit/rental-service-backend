package org.unibl.etf.ip.rentalservice.model.dto;

import lombok.*;
import org.unibl.etf.ip.rentalservice.model.enums.UserType;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Employee extends User {
    private UserType role;
    private List<Promotion> promotions;
}
