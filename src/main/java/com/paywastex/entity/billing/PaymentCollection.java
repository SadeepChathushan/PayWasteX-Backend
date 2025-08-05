package com.paywastex.entity.billing;

import com.paywastex.entity.auth.OurUsers;
import com.paywastex.enums.CollectionStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_collections")
@Data

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private CollectionStatus status; // Enum: COLLECTED, PAID
}
