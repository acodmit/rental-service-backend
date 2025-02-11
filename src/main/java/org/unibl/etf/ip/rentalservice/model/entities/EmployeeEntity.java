package org.unibl.etf.ip.rentalservice.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.unibl.etf.ip.rentalservice.core.BaseEntity;
import org.unibl.etf.ip.rentalservice.model.enums.UserType;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "employee")
@PrimaryKeyJoinColumn(name = "id")
public class EmployeeEntity extends UserEntity{
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType role;
    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<PromotionEntity> promotions;

}