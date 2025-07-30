package com.paywastex.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table(name = "fee_structure ", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id"})
})
@Data
@Entity
public class FeeStructure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private  int id;

    @Column(length = 255, nullable = false)
    private  String premiseType;

    @Column(length = 255, nullable = false)
    private String businessCategory;

    @Column(nullable = false)
    private double feeAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
