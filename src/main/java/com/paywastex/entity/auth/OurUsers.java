package com.paywastex.entity.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Table(name = "ourusers", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id", "role"}) // Ensure uniqueness between id and role
})
@Data
@Entity
public class OurUsers implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;  // Auto-generated id

    @Column(length = 255, nullable = false)
    private String role;  // No longer part of the primary key

    @Column(  length = 255)
    private String fullName;

    @Column( length = 255)
    private String email;

    @Column( length = 255)
    private String address;

    @Column( length = 255)
    private String contactNo;

    @Column( length = 512)
    private String password;

    @Column(length = 15)
    private String nic;

    @Column(  columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column
    private String gender;

    @Column
    private Date dob;

    @Column(nullable = false)
    private boolean accountNonLocked = true;

    @Column(nullable = false)
    private int failedAttempt = 0;

    private Instant lockTime;

    public void incFailed() {
        this.failedAttempt++;
    }

    public void resetFailed() {
        this.failedAttempt = 0;
    }




    public boolean isLocked() {
        if (!accountNonLocked && lockTime != null &&
                Instant.now().isAfter(lockTime.plus(15, ChronoUnit.MINUTES))) {
            // auto-unlock after 15 min
            accountNonLocked = true;
            resetFailed();
        }
        return !accountNonLocked;
    }



    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return this.email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public String getPassword() {
        return password;
    }

    // In your OurUsers entity
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefreshToken> refreshTokens;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Customer customer;


}

