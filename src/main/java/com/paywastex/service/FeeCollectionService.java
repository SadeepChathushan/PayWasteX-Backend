package com.paywastex.service;


import com.paywastex.dto.DashboardSummaryDto;
import com.paywastex.dto.FeeCollectionCardDto;

import java.util.List;

public interface FeeCollectionService {
    List<FeeCollectionCardDto> getCardsByZone(String zoneName);
    DashboardSummaryDto getDashboardSummary();
}
