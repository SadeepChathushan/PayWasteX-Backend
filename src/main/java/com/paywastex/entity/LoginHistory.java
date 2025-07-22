package com.paywastex.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class LoginHistory {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private OurUsers userId;

    private LocalDateTime loginTime;

}