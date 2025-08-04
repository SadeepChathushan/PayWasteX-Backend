package com.paywastex.controller;

import com.paywastex.dto.request.DirectCustomerPaymentRequest;
import com.paywastex.entity.DirectCustomerPayment;
import com.paywastex.service.ResposibleOfficerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/responsibleOfficer")
public class ResposibleOfficerController {

    @Autowired
    private ResposibleOfficerService responsibleOfficerService;

    @PostMapping("/payments")
    public ResponseEntity<String> createPayment(@RequestBody DirectCustomerPaymentRequest paymentRequest) {
        responsibleOfficerService.createDirectCustomerPayment(paymentRequest);
        return ResponseEntity.ok("Payment successfully added");
    }

//    @GetMapping("/collections")
//    public ResponseEntity<List<PaymentCollectionResponse>> getCollectionsByCollector(
//            @RequestParam Long collectorId) {
//        List<PaymentCollectionResponse> collections = responsibleOfficerService.getCollectionsByCollectorId(collectorId);
//        return ResponseEntity.ok(collections);
//    }

    @PutMapping("/collections/{id}/mark-paid")
    public ResponseEntity<String> markCollectionAsPaid(@PathVariable Long id) {
        responsibleOfficerService.markCollectionAsPaid(id);
        return ResponseEntity.ok("Collection marked as PAID. Payment updated if applicable.");
    }


}