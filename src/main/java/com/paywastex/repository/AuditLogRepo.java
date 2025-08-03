package com.paywastex.repository;

import com.paywastex.entity.logging.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepo extends JpaRepository<AuditLog,Long> {

}

