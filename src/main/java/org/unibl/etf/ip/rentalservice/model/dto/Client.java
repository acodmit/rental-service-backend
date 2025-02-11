package org.unibl.etf.ip.rentalservice.model.dto;

import lombok.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Client extends User {
    private Integer id;
    private String avatarUrl;
    private String idCardNumber;
    private Boolean isBlocked;
    //private List<Rental> rentals;
}
