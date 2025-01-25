package org.unibl.etf.ip.rentalservice.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.unibl.etf.ip.rentalservice.core.BaseEntity;

@Data
@Entity
@Table(name = "bike")
public class BikeEntity implements BaseEntity<Integer> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "model", nullable = false, length = 255)
    private String model;
    @Basic
    @Column(name = "range_km", nullable = false)
    private Integer rangeKm;
    @OneToOne
    @JoinColumn(name = "bike_id", referencedColumnName = "id", nullable = false)
    private VehicleEntity vehicle;

}
