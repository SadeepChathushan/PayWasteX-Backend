package com.paywastex.repository;

import com.paywastex.entity.billing.Payment;
import com.paywastex.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findByCustomer_Zone_Id(Long zoneId);

    long countByStatus(PaymentStatus status);

//    @Query("SELECT COALESCE(SUM(p.paidAmount),0) FROM Payment p WHERE p.paidAmount IS NOT NULL")
//    BigDecimal sumAllPaidAmounts();
//
//    @Query("SELECT COALESCE(SUM(p.paidAmount),0) FROM Payment p WHERE DATE(p.collectedDate) = :today")
//    BigDecimal sumPaidAmountByCollectedDate(LocalDate today);
}
