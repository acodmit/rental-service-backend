package org.unibl.etf.ip.rentalservice.model.dto;

import lombok.*;
import java.util.List;

@Data
public class User {
    private Integer id;
    private String username;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Client client;
    private Employee employee;
    private List<LoginSession> loginSessions;

}
