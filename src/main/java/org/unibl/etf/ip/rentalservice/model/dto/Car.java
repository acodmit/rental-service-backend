package org.unibl.etf.ip.rentalservice.model.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class Car extends Vehicle{
    private Integer id;
    private String description;

}
