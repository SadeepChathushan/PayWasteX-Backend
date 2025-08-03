package com.paywastex.service.IMPL;

import com.paywastex.dto.request.DirectCustomerPaymentRequest;
import com.paywastex.entity.DirectCustomerPayment;
import com.paywastex.entity.OurUsers;
import com.paywastex.repository.DirectCustomerPayRepository;
import com.paywastex.repository.OurUsersRepo;
import com.paywastex.service.ResposibleOfficerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResposibleOfficerServiceIMPL implements ResposibleOfficerService {

    @Autowired
    private DirectCustomerPayRepository paymentRepository;

    @Autowired
    private OurUsersRepo ourUsersRepo;

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

        return paymentRepository.save(payment);
    }
}