package com.paywastex.service;

import com.paywastex.dto.ReqRes;
import com.paywastex.entity.OurUsers;
import com.paywastex.repository.OurUsersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class UsersManagementService {

    private final OurUsersRepo usersRepo;
    private final AuthenticationManager authManager;
    private final PasswordEncoder encoder;
    private final JWTUtils               jwt;
    private final LoginAttemptService    attempts;
    private final RefreshTokenService    refreshSvc;

    /* ---------------------------------------------------------
     * LOGIN
     * --------------------------------------------------------- */
    public ReqRes login(ReqRes in) {

        ReqRes resp = new ReqRes();

        // 1) locate user by e-mail
        OurUsers user = usersRepo.findByEmail(in.getEmail())
                .orElse(null);

        if (user == null) {
            resp.setStatusCode(404);
            resp.setMassage("Email not found");
            return resp;
        }

        if (user.isLocked()) {                         // auto-unlock logic inside isLocked()
            resp.setStatusCode(423);                   // HTTP 423 Locked
            resp.setMassage("Account temporarily locked – try again later");
            return resp;
        }

        /* client IP for audit / brute-force log */
        String ip = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest()
                .getRemoteAddr();

        // 2) verify password
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(in.getEmail(), in.getPassword()));
        } catch (BadCredentialsException bad) {
            attempts.onFailure(user, ip);              // ++fail counter / maybe lock
            resp.setStatusCode(401);
            resp.setMassage("Invalid credentials");
            return resp;
        }

        // 3) success path
        attempts.onSuccess(user, ip);                  // reset counter & audit OK
        String access  = jwt.generateToken(user);      // 5-min access token
        String refresh = refreshSvc.issue(user);       // 7-day refresh token


        resp.setStatusCode(200);
        resp.setMassage("Logged-in");
        resp.setToken(access);
        resp.setRefreshToken(refresh);
        resp.setExpirationTime("5 min");
        resp.setRole(user.getRole());
        resp.setUserId(user.getId());
        return resp;
    }

    /* ---------------------------------------------------------
     * REFRESH TOKEN
     * --------------------------------------------------------- */
    public ReqRes refreshToken(ReqRes in) {

        ReqRes resp = new ReqRes();

        try {
            OurUsers user   = refreshSvc.validateAndRotate(in.getToken()); // revokes old
            String   access = jwt.generateToken(user);
            String   rtn    = refreshSvc.issue(user);

            resp.setStatusCode(200);
            resp.setMassage("Token refreshed");
            resp.setToken(access);
            resp.setRefreshToken(rtn);
            resp.setExpirationTime("5 min");
            resp.setRole(user.getRole());
            resp.setUserId(user.getId());
        } catch (RuntimeException ex) {
            resp.setStatusCode(401);
            resp.setMassage(ex.getMessage());
        }
        return resp;
    }

    /* ---------------------------------------------------------
     * USER REGISTER
     * --------------------------------------------------------- */
    public ReqRes userRegister(ReqRes in) {

        ReqRes resp = new ReqRes();

        if (in.getPassword() == null || in.getPassword().isBlank()) {
            resp.setStatusCode(400);
            resp.setMassage("Password cannot be empty");
            return resp;
        }
        if (usersRepo.existsByEmail(in.getEmail())) {
            resp.setStatusCode(409);
            resp.setMassage("Email already registered");
            return resp;
        }

        OurUsers u = new OurUsers();
        u.setEmail(in.getEmail());
        u.setFullName(in.getFullName());
        u.setNic(in.getNic());
        u.setContactNo(in.getContactNo());
        u.setDob(in.getDob());
        u.setGender(in.getGender());
        u.setAddress(in.getAddress());
        u.setRole(in.getRole());
        u.setCreatedAt(new Date());
        u.setPassword(encoder.encode(in.getPassword()));

        usersRepo.save(u);

        resp.setStatusCode(201);
        resp.setMassage("User registered");
        resp.setOurUsers(u);
        return resp;
    }
}
