package com.paywastex.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "zone")
public class Zone {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "assigned_officer_id")
    private OurUsers assignedOfficer;

    // Reverse relation to customers (optional)
    @OneToMany(mappedBy = "zone")
    private List<Customer> customers;
}

