package org.unibl.etf.ip.rentalservice.model.requests;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.unibl.etf.ip.rentalservice.model.dto.Client;
import org.unibl.etf.ip.rentalservice.model.dto.Employee;
import org.unibl.etf.ip.rentalservice.model.dto.LoginSession;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String username;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    //private List<LoginSession> loginSessions;
}
