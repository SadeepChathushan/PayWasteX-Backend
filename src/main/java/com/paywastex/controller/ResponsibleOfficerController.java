package com.paywastex.controller;

import com.paywastex.dto.*;
import com.paywastex.dto.request.AddZoneRequest;
import com.paywastex.dto.request.DirectCustomerPaymentRequest;
import com.paywastex.dto.request.ResponsibleOfficerCustomerRegisterRequest;
import com.paywastex.entity.auth.OurUsers;
import com.paywastex.service.ResponsibleOfficerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/responsibleOfficer")
public class ResponsibleOfficerController {

    @Autowired
    private ResponsibleOfficerService responsibleOfficerService;

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


    @GetMapping("/collectors/total-collected")
    public List<CollectorTotalResponse> getAllCollectorTotals() {
        return responsibleOfficerService.getCollectorTotals();
    }
    @GetMapping("/total-active-fee-collectors")
    public ResponseEntity<DashboardCardResponse> getTotalActiveFeeCollectors() {
        DashboardCardResponse response = responsibleOfficerService.getTotalActiveFeeCollectors();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/bill-management-stats")
    public ResponseEntity<BillManagementCardResponse> getBillManagementStatistics() {
        BillManagementCardResponse response = responsibleOfficerService.getBillManagemntStatistics();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-zone")
    public ResponseEntity<String> addZone(@RequestBody AddZoneRequest addZoneRequest) {
        responsibleOfficerService.zone(addZoneRequest);
        return ResponseEntity.ok("Zone successfully added");
    }
    @GetMapping("/get-zone")
    public ResponseEntity<List<ZoneResponse>> getAllZones() {
        List<ZoneResponse> zones = responsibleOfficerService.getAllZones();
        return ResponseEntity.ok(zones);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody ResponsibleOfficerCustomerRegisterRequest request) {
        responsibleOfficerService.registerCustomer(request);
        return ResponseEntity.ok("Customer registered successfully");
    }

    @GetMapping("/All-Active-Customers")
    public ResponseEntity<List<ActiveAllCustomerResponse>> getAllCustomers() {
        List<ActiveAllCustomerResponse> response = responsibleOfficerService.getAllCustomers();
        return ResponseEntity.ok(response);
    }


}

