package com.paywastex.entity.billing;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Data
@Entity
@Immutable // Hibernate-specific: prevents update/insert/delete
@Table(name = "payment_summary")

public class PaymentSummary {

    @Id
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "total_paid")
    private BigDecimal totalPaid;

    @Column(name = "amount_due")
    private BigDecimal amountDue;

    // Getters only (no setters needed if it's read-only)
}
