package org.unibl.etf.ip.rentalservice.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.unibl.etf.ip.rentalservice.core.BaseEntity;
import org.unibl.etf.ip.rentalservice.model.enums.VehicleType;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "vehicle")
public class VehicleEntity implements BaseEntity<Integer> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType type;
    @Basic
    @Column(name = "acquisition_date", nullable = false)
    private Date acquisitionDate;
    @Basic
    @Column(name = "purchase_price", nullable = false, precision = 2)
    private BigDecimal purchasePrice;
    @Basic
    @Column(name = "description", nullable = true, length = -1)
    private String description;
    @Basic
    @Column(name = "is_broken", nullable = true)
    private Boolean isBroken;
    @Basic
    @Column(name = "image_url", nullable = true, length = 255)
    private String imageUrl;
    @OneToOne(mappedBy = "vehicle")
    private BikeEntity bike;
    @OneToOne(mappedBy = "vehicle")
    private CarEntity car;
    @OneToMany(mappedBy = "vehicle")
    private List<FaultEntity> faults;
    @OneToMany(mappedBy = "vehicle")
    private List<RentalEntity> rentals;
    @OneToOne(mappedBy = "vehicle")
    private ScooterEntity scooter;
    @ManyToOne
    @JoinColumn(name = "manufacturer_id", referencedColumnName = "id", nullable = false)
    private ManufacturerEntity manufacturer;

}
