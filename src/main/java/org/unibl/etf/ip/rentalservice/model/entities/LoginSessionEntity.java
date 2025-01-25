package org.unibl.etf.ip.rentalservice.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.unibl.etf.ip.rentalservice.core.BaseEntity;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "login_session")
public class LoginSessionEntity implements BaseEntity<Integer> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "login_time", nullable = false)
    private Timestamp loginTime;
    @Basic
    @Column(name = "logout_time", nullable = true)
    private Timestamp logoutTime;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

}
