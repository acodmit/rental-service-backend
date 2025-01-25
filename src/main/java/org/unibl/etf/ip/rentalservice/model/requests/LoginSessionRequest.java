package org.unibl.etf.ip.rentalservice.model.requests;

import org.unibl.etf.ip.rentalservice.model.dto.User;

import java.sql.Timestamp;

public class LoginSessionRequest {
    private Timestamp loginTime;
    private Timestamp logoutTime;
    private User user;
}
