package com.paywastex.repository;

import com.paywastex.entity.billing.PaymentSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentSummaryRepository extends JpaRepository<PaymentSummary, Long> {
    Optional<PaymentSummary> findByPaymentId(Long paymentId);
}
