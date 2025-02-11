package org.unibl.etf.ip.rentalservice.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.unibl.etf.ip.rentalservice.model.enums.UserType;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest extends UserRequest {
    private UserType role;
    //private List<Promotion> promotions;
}
