package org.unibl.etf.ip.rentalservice.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.unibl.etf.ip.rentalservice.core.BaseEntity;
import org.unibl.etf.ip.rentalservice.model.enums.UserType;

import java.util.List;

@Data
@Entity
@Table(name = "employee")
public class EmployeeEntity implements BaseEntity<Integer> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType role;
    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;
    @OneToMany(mappedBy = "employee")
    private List<PromotionEntity> promotions;

}
