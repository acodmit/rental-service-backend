package org.unibl.etf.ip.rentalservice.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.unibl.etf.ip.rentalservice.core.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "car")
@PrimaryKeyJoinColumn(name = "id")
@DiscriminatorValue("CAR")
public class CarEntity extends VehicleEntity {
    @Basic
    @Column(name = "description", nullable = false, length = 255)
    private String description;
}