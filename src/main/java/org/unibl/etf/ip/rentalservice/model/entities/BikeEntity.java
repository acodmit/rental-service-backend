package org.unibl.etf.ip.rentalservice.model.entities;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table( name = "bike")
@PrimaryKeyJoinColumn( name = "id")
@DiscriminatorValue("BIKE")
public class BikeEntity extends VehicleEntity {
    @Basic
    @Column(name = "range_km", nullable = false)
    private Integer rangeKm;
}
