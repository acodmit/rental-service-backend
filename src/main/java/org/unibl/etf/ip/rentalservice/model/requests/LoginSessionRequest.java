package org.unibl.etf.ip.rentalservice.model.requests;

import lombok.Data;
import org.unibl.etf.ip.rentalservice.model.dto.User;

import java.sql.Timestamp;

@Data
public class LoginSessionRequest {
    private Timestamp loginTime;
    private Timestamp logoutTime;
    private Integer userId;
}
