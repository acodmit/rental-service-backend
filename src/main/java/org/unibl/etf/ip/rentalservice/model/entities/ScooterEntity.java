package org.unibl.etf.ip.rentalservice.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.unibl.etf.ip.rentalservice.core.BaseEntity;

@Data
@Entity
@Table(name = "scooter")
public class ScooterEntity implements BaseEntity<Integer> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "model", nullable = false, length = 255)
    private String model;
    @Basic
    @Column(name = "max_speed_kmh", nullable = false)
    private Integer maxSpeedKmh;
    @OneToOne
    @JoinColumn(name = "scooter_id", referencedColumnName = "id", nullable = false)
    private VehicleEntity vehicle;

}
