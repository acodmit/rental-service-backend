package org.unibl.etf.ip.rentalservice.model.dto;

import lombok.*;
import java.util.List;

@Data
public class Manufacturer {
    private Integer id;
    private String name;
    private String country;
    private String address;
    private String phoneNumber;
    private String fax;
    private String email;

}