package com.paywastex.repository;

import com.paywastex.entity.billing.PaymentCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PaymentCollectionRepository extends JpaRepository<PaymentCollection, Long> {

    // Existing methods
    @Query("SELECT SUM(pc.collectedAmount) FROM PaymentCollection pc WHERE DATE(pc.collectedDate) = :date")
    BigDecimal sumCollectedAmountByDate(@Param("date") LocalDate date);

    @Query("SELECT SUM(pc.collectedAmount) FROM PaymentCollection pc")
    BigDecimal sumAllCollectedAmount();

    @Query("SELECT pc.collector.id, pc.collector.fullName, SUM(pc.collectedAmount) " +
            "FROM PaymentCollection pc GROUP BY pc.collector.id, pc.collector.fullName")
    List<Object[]> getCollectorWiseTotalsWithName();

    @Query("SELECT SUM(pc.collectedAmount) FROM PaymentCollection pc")
    BigDecimal getTotalCollectedAmount();

    // New method to support existing service
    @Query("SELECT SUM(pc.collectedAmount) FROM PaymentCollection pc WHERE DATE(pc.collectedDate) = :date")
    BigDecimal sumCollectedAmountedToday(@Param("date") LocalDate date);
}