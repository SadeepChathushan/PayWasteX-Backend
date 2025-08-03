package com.paywastex.repository;

import com.paywastex.entity.billing.PaymentCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface PaymentCollectionRepository extends JpaRepository<PaymentCollection, Long> {

    @Query("SELECT COALESCE(SUM(pc.collectedAmount), 0)FROM PaymentCollection pc WHERE FUNCTION('DATE', pc.collectedDate) = :today")
    BigDecimal sumCollectedAmountedToday(LocalDate today);

    @Query("SELECT COALESCE(SUM(pc.collectedAmount), 0) FROM PaymentCollection pc")
    BigDecimal sumAllCollectedAmount();
}
