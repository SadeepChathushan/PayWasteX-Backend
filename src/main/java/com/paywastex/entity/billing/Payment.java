package com.paywastex.entity.billing;

import com.paywastex.entity.customer.Customer;
import com.paywastex.entity.auth.OurUsers;
import com.paywastex.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collector_id", nullable = false)
    private OurUsers collector;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private PaymentStatus status;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    private List<PaymentCollection> collections;

}
