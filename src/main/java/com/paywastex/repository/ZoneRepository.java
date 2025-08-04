package com.paywastex.repository;

import com.paywastex.dto.ZoneResponse;
import com.paywastex.entity.auth.OurUsers;
import com.paywastex.entity.customer.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    Zone findByDescription(String description);


}
