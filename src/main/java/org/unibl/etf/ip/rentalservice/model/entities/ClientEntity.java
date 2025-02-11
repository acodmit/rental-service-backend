package org.unibl.etf.ip.rentalservice.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.unibl.etf.ip.rentalservice.core.BaseEntity;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "client")
@PrimaryKeyJoinColumn(name = "id")
public class ClientEntity extends UserEntity{
    @Basic
    @Column(name = "avatar_url", nullable = true, length = 255)
    private String avatarUrl;
    @Basic
    @Column(name = "id_card_number", nullable = false, length = 20)
    private String idCardNumber;
    @Basic
    @Column(name = "is_blocked", nullable = true)
    private Boolean isBlocked;
    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<RentalEntity> rentals;

}
