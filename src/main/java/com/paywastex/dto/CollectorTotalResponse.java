package com.paywastex.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CollectorTotalResponse {
    private Long collectorId;
    private String collectorName;
    private String totalCollectedAmount;
}
