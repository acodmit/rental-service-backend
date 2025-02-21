package org.unibl.etf.ip.rentalservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class RevenueByVehicleType {
    private String vehicleType;
    private BigDecimal totalRevenue;
    private List<RevenueByDay> revenueByDay;
}
