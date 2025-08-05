package com.paywastex.controller;


import com.paywastex.dto.DashboardSummaryDto;
import com.paywastex.dto.FeeCollectionCardDto;
import com.paywastex.service.FeeCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/feeCollector")
public class FeeCollectionController {
    @Autowired
    private FeeCollectionService feeCollectionService;

    @GetMapping("/dashboard/cards")
    public ResponseEntity<List<FeeCollectionCardDto>> getFeeCollectionCardsByZone(@RequestParam String zoneName) {
        List<FeeCollectionCardDto> cards = feeCollectionService.getCardsByZone(zoneName);
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/dashboard/summary")
    public DashboardSummaryDto getDashboardSummary(){
        return feeCollectionService.getDashboardSummary();
    }

}
