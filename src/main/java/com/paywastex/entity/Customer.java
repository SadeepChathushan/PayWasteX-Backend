package com.paywastex.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Customer {
    @Id
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name="id")
    private OurUsers user;

    @Column(unique = true)
    private String customerCode;

    private String businessName;

    private String businessType;

    private String registrationNumber;

    private String location;

    private String city;

    @ManyToOne
    @JoinColumn(name="zone_id")
    private Zone zone;

    @ManyToOne
    @JoinColumn(name = "collector_id")
    private OurUsers assignedCollector;

    private String paymentMethod;


}
