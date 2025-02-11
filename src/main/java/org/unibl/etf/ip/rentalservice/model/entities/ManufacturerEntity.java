package org.unibl.etf.ip.rentalservice.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.unibl.etf.ip.rentalservice.core.BaseEntity;

import java.util.List;

@Data
@Entity
@Table(name = "manufacturer")
public class ManufacturerEntity implements BaseEntity<Integer> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "country", nullable = true, length = 100)
    private String country;
    @Basic
    @Column(name = "address", nullable = true, length = 255)
    private String address;
    @Basic
    @Column(name = "phone_number", nullable = true, length = 50)
    private String phoneNumber;
    @Basic
    @Column(name = "fax", nullable = true, length = 50)
    private String fax;
    @Basic
    @Column(name = "email", nullable = true, length = 100)
    private String email;
    @OneToMany(mappedBy = "manufacturer")
    @JsonIgnore
    private List<VehicleEntity> vehicles;

}
