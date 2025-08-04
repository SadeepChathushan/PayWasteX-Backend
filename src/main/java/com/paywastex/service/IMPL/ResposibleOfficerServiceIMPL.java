package com.paywastex.service.IMPL;

import com.paywastex.dto.request.DirectCustomerPaymentRequest;
import com.paywastex.entity.DirectCustomerPayment;
import com.paywastex.entity.auth.OurUsers;
import com.paywastex.entity.billing.Payment;
import com.paywastex.entity.billing.PaymentCollection;
import com.paywastex.entity.billing.PaymentSummary;
import com.paywastex.enums.CollectionStatus;
import com.paywastex.enums.PaymentStatus;
import com.paywastex.repository.*;
import com.paywastex.service.ResposibleOfficerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

}