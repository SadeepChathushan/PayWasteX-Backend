package com.paywastex.scheduled;

import com.paywastex.entity.auth.OurUsers;
import com.paywastex.repository.OurUsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserUnlockScheduler {
    private final OurUsersRepo usersRepo;

    @Scheduled(fixedRate = 60000) // Every 1 minute
    public void unlockUsers() {
        Instant now = Instant.now();
        List<OurUsers> lockedUsers = usersRepo.findAllByAccountNonLockedFalseAndLockTimeIsNotNull();

        for (OurUsers user : lockedUsers) {
            if (user.getLockTime() != null && user.getLockTime().plusSeconds(900).isBefore(now)) { // 15 mins = 900 sec
                user.setAccountNonLocked(true);
                user.setLockTime(null);
                user.setFailedAttempt(0);
                usersRepo.save(user);
                System.out.println("[AUTO-UNLOCK] User unlocked: " + user.getEmail());
            }
        }
    }
}