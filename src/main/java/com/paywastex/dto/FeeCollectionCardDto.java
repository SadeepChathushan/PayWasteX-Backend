package com.paywastex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FeeCollectionCardDto {
    private String code;
    private String fullName;
    private String businessName;
    private String businessType;
    private BigDecimal outstandingDue;
    private String status;
    private Long zoneId;
}
