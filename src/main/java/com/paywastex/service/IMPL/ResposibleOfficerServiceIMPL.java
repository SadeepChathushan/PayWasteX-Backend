package com.paywastex.service.IMPL;

import com.paywastex.dto.*;
import com.paywastex.dto.request.AddZoneRequest;
import com.paywastex.dto.request.DirectCustomerPaymentRequest;
import com.paywastex.dto.request.ResponsibleOfficerCustomerRegisterRequest;
import com.paywastex.entity.DirectCustomerPayment;
import com.paywastex.entity.auth.OurUsers;

import com.paywastex.entity.billing.Payment;
import com.paywastex.entity.billing.PaymentCollection;

import com.paywastex.entity.customer.Customer;
import com.paywastex.entity.customer.Zone;

import com.paywastex.entity.billing.PaymentSummary;
import com.paywastex.enums.CollectionStatus;

import com.paywastex.enums.PaymentStatus;
import com.paywastex.repository.*;
import com.paywastex.service.ResposibleOfficerService;
import jakarta.transaction.Transactional;

import com.paywastex.entity.customer.Zone;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class ResposibleOfficerServiceIMPL implements ResposibleOfficerService {

    @Autowired
    private DirectCustomerPayRepository directCustomerPayRepository;

    @Autowired

    private PaymentCollectionRepository paymentCollectionRepository;

    @Autowired

    private PaymentRepository paymentRepository;

    @Autowired
    private OurUsersRepo ourUsersRepo;

    @Autowired
    private PaymentSummaryRepository paymentSummaryRepository;



    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private  CustomerRepository customerRepository;


    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public DirectCustomerPayment createDirectCustomerPayment(DirectCustomerPaymentRequest paymentRequest) {
        OurUsers user = ourUsersRepo.findById(paymentRequest.getUser())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + paymentRequest.getUser()));

        DirectCustomerPayment payment = new DirectCustomerPayment();
        payment.setAreaZone(paymentRequest.getAreaZone());
        payment.setBillId(paymentRequest.getBillId());
        payment.setCompanyName(paymentRequest.getCompanyName());
        payment.setRegisterNo(paymentRequest.getRegisterNo());
        payment.setCustomerName(paymentRequest.getCustomerName());
        payment.setAmountPaid(paymentRequest.getAmountPaid());
        payment.setReceiptNumber(paymentRequest.getReceiptNumber());
        payment.setUser(user);

        return directCustomerPayRepository.save(payment);

    }

    @Transactional
    public void markCollectionAsPaid(Long collectionId) {
        PaymentCollection collection = paymentCollectionRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("Collection not found"));

        collection.setStatus(CollectionStatus.PAID);
        paymentCollectionRepository.save(collection);

        // Now check if Payment can be marked as PAID
        Payment payment = collection.getPayment();

        BigDecimal amountDue = paymentSummaryRepository
                .findById(payment.getId())
                .map(PaymentSummary::getAmountDue)
                .orElse(BigDecimal.ZERO); // Or throw if not found

        boolean allCollectionsPaid = payment.getCollections()
                .stream()
                .allMatch(c -> c.getStatus() == CollectionStatus.PAID);

        if (amountDue.compareTo(BigDecimal.ZERO) == 0 && allCollectionsPaid) {
            payment.setStatus(PaymentStatus.PAID);
            paymentRepository.save(payment);
        }
    }

    @Override
    public List<CollectorTotalResponse> getCollectorTotals() {
        List<Object[]> results = paymentCollectionRepository.getCollectorWiseTotalsWithName();

        return results.stream()
                .map(result -> {
                    CollectorTotalResponse response = new CollectorTotalResponse();
                    response.setCollectorId(((Integer) result[0]).longValue()); // Convert int to long
                    response.setCollectorName((String) result[1]); // Maps to fullName
                    response.setTotalCollectedAmount(((BigDecimal) result[2]).toString());
                    return response;
                })
                .collect(Collectors.toList());
    }


    @Override
    public DashboardCardResponse getTotalActiveFeeCollectors() {
        long count = ourUsersRepo.countByAccountNonLockedTrueAndRole("FEECOLLECTOR");

        DashboardCardResponse response = new DashboardCardResponse();
        response.setTotalFeeCollector(String.valueOf(count));
        return response;
    }


    @Override
    public BillManagementCardResponse getBillManagemntStatistics() {
        BillManagementCardResponse response = new BillManagementCardResponse();

        // Get total count of all bills (Payment entities)
        long totalBills = paymentRepository.count();
        response.setTotalBills(String.valueOf(totalBills));

        // Get count of pending bills (Payment entities)
        long pendingBills = paymentRepository.countByStatus(PaymentStatus.PENDING);
        response.setTotalPendingPayment(String.valueOf(pendingBills));

        // Get count of overdue bills (Payment entities)
        long overdueBills = paymentRepository.countByStatusAndDueDateBefore(
                PaymentStatus.PENDING,
                LocalDate.now()
        );
        response.setTotalOverdueBills(String.valueOf(overdueBills));

        return response;
    }

    @Override
    public Zone zone(AddZoneRequest addZoneRequest) {
        Zone zone = new Zone();
        zone.setDescription(addZoneRequest.getDescription());
        zone.setZoneCode(addZoneRequest.getZoneCode());
        zone.setZoneName(addZoneRequest.getZoneName());
        zone.setActive(true);
        return zoneRepository.save(zone);
    }

    @Override
    public List<ZoneResponse> getAllZones() {
        List<Zone> zones = zoneRepository.findAll();
        return zones.stream()
                .map(zone -> modelMapper.map(zone, ZoneResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public OurUsers registerCustomer(ResponsibleOfficerCustomerRegisterRequest request){
        Zone  zone  = zoneRepository.findById(request.getZoneId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUser()));

        OurUsers user = new OurUsers();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        user.setContactNo(request.getContactNo());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNic(request.getNic());
        user.setRole("CUSTOMER");
        user.setAccountNonLocked(true);

        OurUsers savedUser = ourUsersRepo.save(user);

        Customer customer = new Customer();
        customer.setBusinessName(request.getBusinessName());
        customer.setRegistrationNumber(request.getRegistrationNumber());
        customer.setBusinessType(request.getBusinessType());
        customer.setCity(request.getCity());
        customer.setZone(zone);
        customer.setUser(savedUser);

        customerRepository.save(customer);

        return savedUser;

    }

}

