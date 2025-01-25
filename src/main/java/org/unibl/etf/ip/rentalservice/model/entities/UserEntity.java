package org.unibl.etf.ip.rentalservice.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.unibl.etf.ip.rentalservice.core.BaseEntity;

import java.util.List;

@Data
@Entity
@Table(name = "user")
public class UserEntity implements BaseEntity<Integer> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "username", nullable = false, length = 50)
    private String username;
    @Basic
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;
    @Basic
    @Column(name = "first_name", nullable = true, length = 50)
    private String firstName;
    @Basic
    @Column(name = "last_name", nullable = true, length = 50)
    private String lastName;
    @Basic
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    @Basic
    @Column(name = "phone_number", nullable = true, length = 50)
    private String phoneNumber;
    @OneToOne(mappedBy = "user")
    private ClientEntity client;
    @OneToOne(mappedBy = "user")
    private EmployeeEntity employee;
    @OneToMany(mappedBy = "user")
    private List<LoginSessionEntity> loginSessions;

}
