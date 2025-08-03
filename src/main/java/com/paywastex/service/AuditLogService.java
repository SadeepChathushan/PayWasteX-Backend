package com.paywastex.service;

import com.paywastex.entity.logging.AuditLog;
import com.paywastex.repository.AuditLogRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuditLogService {
    private final AuditLogRepo repo;

    @Async
    public void log(String actor, String action, String ip, String details){
        repo.save(new AuditLog(null, Instant.now(),actor,action,ip,details));
    }
}
