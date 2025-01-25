package org.unibl.etf.ip.rentalservice.model.dto;

import lombok.*;
import java.util.List;

@Data
public class Client {
    private Integer id;
    private String avatarUrl;
    private String idCardNumber;
    private User user;
    private List<Rental> rentals;

}
