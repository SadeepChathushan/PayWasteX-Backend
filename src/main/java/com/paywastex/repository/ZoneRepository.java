package com.paywastex.repository;

import com.paywastex.entity.customer.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    Zone findByDescription(String description);
}
