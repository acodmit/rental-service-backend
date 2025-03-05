package org.unibl.etf.ip.rentalservice.model.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.unibl.etf.ip.rentalservice.core.BaseEntity;
import org.unibl.etf.ip.rentalservice.model.dto.Bike;
import org.unibl.etf.ip.rentalservice.model.dto.Car;
import org.unibl.etf.ip.rentalservice.model.dto.Scooter;
import org.unibl.etf.ip.rentalservice.model.enums.VehicleType;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "vehicle")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class VehicleEntity implements BaseEntity<Integer> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "acquisition_date", nullable = false)
    private Date acquisitionDate;
    @Basic
    @Column(name = "purchase_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal purchasePrice;
    @Basic
    @Column(name = "is_broken", nullable = true)
    private Boolean isBroken;
    @Basic
    @Column(name = "image_url", nullable = true, length = 255)
    private String imageUrl;
    @Basic
    @Column(name = "model", nullable = false, length = 255)
    private String model;
    @Basic
    @Column(name = "hourly_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal hourlyRate;
    @JsonIgnore
    @OneToMany(mappedBy = "vehicle")
    private List<FaultEntity> faults;
    @JsonIgnore
    @OneToMany(mappedBy = "vehicle")
    private List<RentalEntity> rentals;
    @ManyToOne
    @JoinColumn(name = "manufacturer_id", referencedColumnName = "id", nullable = false)
    private ManufacturerEntity manufacturer;
}
