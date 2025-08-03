package com.paywastex.entity;

import com.paywastex.entity.auth.OurUsers;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "direct_customer_payments")
public class DirectCustomerPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "area_zone", nullable = false)
    private String areaZone; // Field for "Area/Zone"

    @Column(name = "bill_id", nullable = false)
    private String billId; // Field for "Bill ID"

    @Column(name = "company_name", nullable = false)
    private String companyName; // Field for "Company Name"

    @Column(name = "register_no", nullable = false)
    private String registerNo; // Field for "Register No"

    @Column(name = "customer_name", nullable = false)
    private String customerName; // Field for "Customer Name"

    @Column(name = "amount_paid", nullable = false)
    private Double amountPaid; // Field for "Amount Paid"

    @Column(name = "receipt_number", nullable = false)
    private String receiptNumber; // Field for "Receipt Number"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private OurUsers user; // Foreign key referencing OurUsers

}
