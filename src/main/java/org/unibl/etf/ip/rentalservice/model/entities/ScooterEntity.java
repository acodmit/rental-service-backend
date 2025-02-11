package org.unibl.etf.ip.rentalservice.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.unibl.etf.ip.rentalservice.core.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table( name = "scooter")
@PrimaryKeyJoinColumn( name = "id")
@DiscriminatorValue("SCOOTER")
public class ScooterEntity extends VehicleEntity {
    @Basic
    @Column(name = "max_speed_kmh", nullable = false)
    private Integer maxSpeedKmh;
}
