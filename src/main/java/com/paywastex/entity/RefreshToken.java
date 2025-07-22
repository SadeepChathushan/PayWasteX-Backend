package com.paywastex.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor               // required by JPA
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(optional=false) private OurUsers user;
    @Column(nullable=false, unique=true) private String token;
    @Column(nullable=false) private Instant expiryDate;
    @Column(nullable=false) private boolean revoked = false;
}