package com.paywastex.entity.billing;

import com.paywastex.entity.auth.OurUsers;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_collections")

public class PaymentCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "collector_id", nullable = false)
    private OurUsers collector;

    @Column(name = "collected_amount", nullable = false)
    private BigDecimal collectedAmount;

    @Column(name = "collected_date")
    private LocalDateTime collectedDate = LocalDateTime.now();
}
