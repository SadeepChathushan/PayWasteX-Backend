package com.paywastex.service;

import com.paywastex.dto.BillManagementCardResponse;
import com.paywastex.dto.CollectorTotalResponse;
import com.paywastex.dto.DashboardCardResponse;
import com.paywastex.dto.request.DirectCustomerPaymentRequest;
import com.paywastex.entity.DirectCustomerPayment;

import java.util.List;

public interface ResposibleOfficerService {
    DirectCustomerPayment createDirectCustomerPayment(DirectCustomerPaymentRequest paymentRequest);
    List<CollectorTotalResponse> getCollectorTotals();
    DashboardCardResponse getTotalActiveFeeCollectors();
    BillManagementCardResponse getBillManagemntStatistics();

}
