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

}