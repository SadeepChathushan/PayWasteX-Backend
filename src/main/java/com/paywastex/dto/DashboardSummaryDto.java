package com.paywastex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data

public class DashboardSummaryDto {
    private long totalCustomers;
    private long pendingPayments;
    private BigDecimal todaysCollections;
    private BigDecimal totalCollections;
}
