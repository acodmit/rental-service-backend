package org.unibl.etf.ip.rentalservice.model.dto;

import lombok.*;
import java.sql.Timestamp;

@Data
public class LoginSession {
    private Integer id;
    private Timestamp loginTime;
    private Timestamp logoutTime;
    //private User user;

}
