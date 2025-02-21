package org.unibl.etf.ip.rentalservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class RevenueByDay {
    private String day;
    private BigDecimal revenue;

}