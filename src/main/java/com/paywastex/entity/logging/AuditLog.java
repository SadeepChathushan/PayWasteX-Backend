package com.paywastex.entity.logging;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name="audit_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLog {
    @Id
    @GeneratedValue
    private Long id;
    private Instant timestamp;
    private String actor;          // email / system
    private String action;         // LOGIN_SUCCESS, ORDER_CREATED, etc.
    private String ip;
    private String details;
}