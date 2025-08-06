package com.paywastex.service;

import com.paywastex.dto.*;
import com.paywastex.dto.request.AddZoneRequest;
import com.paywastex.dto.request.DirectCustomerPaymentRequest;
import com.paywastex.dto.request.ResponsibleOfficerCustomerRegisterRequest;
import com.paywastex.entity.DirectCustomerPayment;
import com.paywastex.entity.auth.OurUsers;
import com.paywastex.entity.customer.Zone;

import java.util.List;

public interface ResponsibleOfficerService {
    DirectCustomerPayment createDirectCustomerPayment(DirectCustomerPaymentRequest paymentRequest);

    void markCollectionAsPaid(Long collectionId);

    List<CollectorTotalResponse> getCollectorTotals();

    DashboardCardResponse getTotalActiveFeeCollectors();

    BillManagementCardResponse getBillManagemntStatistics();

    Zone zone(AddZoneRequest addZoneRequest);

    List<ZoneResponse> getAllZones();

    OurUsers registerCustomer(ResponsibleOfficerCustomerRegisterRequest request);

    List<ActiveAllCustomerResponse> getAllCustomers();

}
