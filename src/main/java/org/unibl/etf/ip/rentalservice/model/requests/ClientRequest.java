package org.unibl.etf.ip.rentalservice.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest extends UserRequest {
    private String avatarUrl;
    private String idCardNumber;
    private Boolean isBlocked;
    //private List<Rental> rentals;
}
