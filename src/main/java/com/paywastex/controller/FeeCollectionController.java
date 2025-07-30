package com.paywastex.controller;


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
@RequestMapping("/feeCollector")
public class FeeCollectionController {
    @Autowired
    private FeeCollectionService feeCollectionService;

    @GetMapping("/dashboard")
    public ResponseEntity<List<FeeCollectionCardDto>> getFeeCollectionCardsByZone(@RequestParam Long zoneId) {
        List<FeeCollectionCardDto> cards = feeCollectionService.getCardsByZone(zoneId);
        return ResponseEntity.ok(cards);
    }
}
