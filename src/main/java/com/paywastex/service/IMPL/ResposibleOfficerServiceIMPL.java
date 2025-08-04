package com.paywastex.service.IMPL;

import com.paywastex.dto.BillManagementCardResponse;
import com.paywastex.dto.CollectorTotalResponse;
import com.paywastex.dto.DashboardCardResponse;
import com.paywastex.dto.request.DirectCustomerPaymentRequest;
import com.paywastex.entity.DirectCustomerPayment;
import com.paywastex.entity.auth.OurUsers;
import com.paywastex.entity.billing.PaymentCollection;
import com.paywastex.enums.PaymentStatus;
import com.paywastex.repository.DirectCustomerPayRepository;
import com.paywastex.repository.OurUsersRepo;
import com.paywastex.repository.PaymentCollectionRepository;
import com.paywastex.repository.PaymentRepository;
import com.paywastex.service.ResposibleOfficerService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PaymentRepository paymentRepository;

    @Autowired
    private OurUsersRepo ourUsersRepo;

    @Autowired
    private PaymentCollectionRepository paymentCollectionRepository;

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
}

