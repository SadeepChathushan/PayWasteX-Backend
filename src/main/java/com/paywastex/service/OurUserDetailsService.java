package com.paywastex.service;


import com.paywastex.entity.auth.OurUsers;
import com.paywastex.repository.OurUsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OurUserDetailsService implements UserDetailsService {
    @Autowired
    private OurUsersRepo ourUsersRepo;
    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return ourUsersRepo.findByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
//    }


    public UserDetails loadUserByUsername(String loginIdentifier) throws UsernameNotFoundException {
        // Check if the identifier is an email or a childId
        Optional<OurUsers> userOpt = ourUsersRepo.findByEmail(loginIdentifier);

        return (UserDetails) userOpt.orElseThrow(() -> new UsernameNotFoundException("User not found with email or childId: " + loginIdentifier));
    }

}
