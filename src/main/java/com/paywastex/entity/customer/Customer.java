package com.paywastex.entity.customer;

import com.paywastex.entity.auth.OurUsers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String businessName;

    private String businessType;

    private String registrationNumber;

    private String city;

    @ManyToOne
    @JoinColumn(name="zone_id")
    private Zone zone;

    @ManyToOne
    @JoinColumn(name = "collector_id")
    private OurUsers assignedCollector;


}
