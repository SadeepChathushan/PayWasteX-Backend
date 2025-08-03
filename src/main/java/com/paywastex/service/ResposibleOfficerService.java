package com.paywastex.service;

import com.paywastex.dto.request.DirectCustomerPaymentRequest;
import com.paywastex.entity.DirectCustomerPayment;

public interface ResposibleOfficerService {
    DirectCustomerPayment createDirectCustomerPayment(DirectCustomerPaymentRequest paymentRequest);
}
