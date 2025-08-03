package com.paywastex.service.IMPL;

import com.paywastex.dto.DashboardSummaryDto;
import com.paywastex.dto.FeeCollectionCardDto;
import com.paywastex.entity.billing.Payment;
import com.paywastex.entity.billing.PaymentSummary;
import com.paywastex.entity.customer.Zone;
import com.paywastex.enums.PaymentStatus;
import com.paywastex.repository.*;
import com.paywastex.service.FeeCollectionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeeCollectionServiceImpl implements FeeCollectionService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private PaymentCollectionRepository paymentCollectionRepository;

    @Autowired
    private PaymentSummaryRepository paymentSummaryRepository;

    @Override
    public List<FeeCollectionCardDto> getCardsByZone(String zoneName){

        Zone zone = zoneRepository.findByDescription(zoneName);

//        if (zone == null){
//            throw new IllegalArgumentException("zone not found " + zoneName);
//        }

        if (zone == null) {
            // Optionally log this
            System.out.println("Zone not found: " + zoneName);
            return Collections.emptyList(); // ✅ Don't throw error, return empty list
        }

        List<Payment> payments = paymentRepository.findByCustomer_Zone_Id(zone.getId());

        return payments.stream().map(payment -> {
            FeeCollectionCardDto dto = modelMapper.map(payment.getCustomer(), FeeCollectionCardDto.class);

//            dto.setOutstandingDue(
//                    payment.getAmountDue().subtract(payment.getPaidAmount() != null ? payment.getPaidAmount() : BigDecimal.ZERO)
//            );

            paymentSummaryRepository.findByPaymentId(payment.getId())
                    .ifPresent(summary -> dto.setOutstandingDue(summary.getAmountDue()));


            dto.setStatus(payment.getStatus().name());
            dto.setZoneName(zoneName);

            // Add full name from ourusers (assuming Customer has a reference to OurUser)
            if (payment.getCustomer().getUser() != null) {
                dto.setFullName(payment.getCustomer().getUser().getFullName());
            }

            // Add business name from customer
            dto.setBusinessName(payment.getCustomer().getBusinessName());

            dto.setBusinessType(payment.getCustomer().getBusinessType());

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public DashboardSummaryDto getDashboardSummary() {
        long totalCustomers = customerRepository.count();

        long pendingPayments = paymentRepository.countByStatus(PaymentStatus.PENDING);

        BigDecimal todaysCollections = paymentCollectionRepository.sumCollectedAmountedToday(LocalDate.now());
        BigDecimal totalCollections = paymentCollectionRepository.sumAllCollectedAmount();

        DashboardSummaryDto dto = new DashboardSummaryDto();

        dto.setTotalCustomers(totalCustomers);
        dto.setPendingPayments(pendingPayments);
        dto.setTodaysCollections(todaysCollections != null ? todaysCollections: BigDecimal.ZERO);
        dto.setTotalCollections(totalCollections != null ? totalCollections : BigDecimal.ZERO);

        return dto;
    }

}
