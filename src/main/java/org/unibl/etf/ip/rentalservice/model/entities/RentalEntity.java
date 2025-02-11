package org.unibl.etf.ip.rentalservice.model.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.unibl.etf.ip.rentalservice.core.BaseEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "rental")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RentalEntity implements BaseEntity<Integer> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;
    @Basic
    @Column(name = "end_date", nullable = true)
    private Timestamp endDate;
    @Basic
    @Column(name = "total_duration_minutes", nullable = true)
    private Integer totalDurationMinutes;
    @Basic
    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;
    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id", nullable = false)
    private VehicleEntity vehicle;
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private ClientEntity client;
    @ManyToOne
    @JoinColumn(name = "pickup_location_id", referencedColumnName = "id", nullable = false)
    private LocationEntity pickUpLocation;
    @ManyToOne
    @JoinColumn(name = "dropoff_location_id", referencedColumnName = "id")
    private LocationEntity dropOffLocation;

}
