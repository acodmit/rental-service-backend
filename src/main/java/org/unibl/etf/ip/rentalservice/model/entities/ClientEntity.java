package org.unibl.etf.ip.rentalservice.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.unibl.etf.ip.rentalservice.core.BaseEntity;

import java.util.List;

@Data
@Entity
@Table(name = "client")
public class ClientEntity implements BaseEntity<Integer> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "avatar_url", nullable = true, length = 255)
    private String avatarUrl;
    @Basic
    @Column(name = "id_card_number", nullable = false, length = 20)
    private String idCardNumber;
    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;
    @OneToMany(mappedBy = "client")
    private List<RentalEntity> rentals;

}
