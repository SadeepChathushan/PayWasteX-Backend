package com.paywastex.repository;

import com.paywastex.entity.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OurUsersRepo extends JpaRepository<OurUsers, Integer> {
    Optional<OurUsers> findByEmail(String email);

    Optional<OurUsers> findByPassword(String password);

    boolean existsByEmail(String email);

    List<OurUsers> findAllByAccountNonLockedFalseAndLockTimeIsNotNull();
}

