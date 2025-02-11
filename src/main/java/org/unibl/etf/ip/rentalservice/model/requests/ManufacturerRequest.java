package org.unibl.etf.ip.rentalservice.model.requests;

import lombok.*;
import org.unibl.etf.ip.rentalservice.model.dto.Vehicle;

import java.util.List;

@Data
public class ManufacturerRequest {
    private String name;
    private String country;
    private String address;
    private String phoneNumber;
    private String fax;
    private String email;
    //private List<Vehicle> vehicles;

}