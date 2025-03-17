package org.unibl.etf.ip.rentalservice.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.unibl.etf.ip.rentalservice.core.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "location")
public class LocationEntity implements BaseEntity<Integer> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "latitude", nullable = false, precision = 10, scale = 8)
    private BigDecimal latitude;
    @Basic
    @Column(name = "longitude", nullable = false, precision = 11, scale = 8)
    private BigDecimal longitude;
    @OneToMany(mappedBy = "pickUpLocation")
    @JsonIgnore
    private List<RentalEntity> pickUpRentals;
    @OneToMany(mappedBy = "dropOffLocation")
    @JsonIgnore
    private List<RentalEntity> dropOffRentals;

}
