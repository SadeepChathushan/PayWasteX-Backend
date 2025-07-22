package com.paywastex.service;

import com.paywastex.entity.OurUsers;
import com.paywastex.entity.RefreshToken;
import com.paywastex.exception.UnauthorizedException;
import com.paywastex.repository.RefreshTokenRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${app.jwt.refresh-ms:604800000}") // 7 days
    private long refreshTtl;

    private final RefreshTokenRepo repo;
    private final JWTUtils jwt;

    @Transactional
    public String issue(OurUsers user){
        String tok = jwt.generateRefreshToken(user, refreshTtl);
        repo.save(new RefreshToken(null,user,tok,
                Instant.now().plusMillis(refreshTtl),false));
        return tok;
    }

    @Transactional
    public OurUsers validateAndRotate(String old){
        RefreshToken stored = repo.findByToken(old)
                .orElseThrow(() -> new UnauthorizedException("Refresh not found"));
        if(stored.isRevoked() || stored.getExpiryDate().isBefore(Instant.now()))
            throw new UnauthorizedException("Refresh expired");

        stored.setRevoked(true);
        repo.save(stored);
        return stored.getUser();
    }
}