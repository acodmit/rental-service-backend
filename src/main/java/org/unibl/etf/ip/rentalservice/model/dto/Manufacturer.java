package org.unibl.etf.ip.rentalservice.model.dto;

import jakarta.persistence.*;
import lombok.*;
import org.unibl.etf.ip.rentalservice.model.entities.VehicleEntity;

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
    private List<Vehicle> vehicles;

}
