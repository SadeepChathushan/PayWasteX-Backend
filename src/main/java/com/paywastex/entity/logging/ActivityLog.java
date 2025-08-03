package com.paywastex.entity.logging;


import com.paywastex.entity.auth.OurUsers;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ActivityLog {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private OurUsers userId;

    private String action;
    private LocalDateTime dateTime;
}