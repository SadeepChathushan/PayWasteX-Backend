package com.paywastex.repository;

import com.paywastex.entity.customer.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    Zone findByDescription(String description);
}
