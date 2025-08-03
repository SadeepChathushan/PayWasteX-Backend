package com.paywastex.entity.customer;


import com.paywastex.entity.auth.OurUsers;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "zone")
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

