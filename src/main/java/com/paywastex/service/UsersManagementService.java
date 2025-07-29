package com.paywastex.service;

import com.paywastex.dto.CustomerRegistration;
import com.paywastex.dto.ReqRes;
import com.paywastex.entity.Customer;
import com.paywastex.entity.OurUsers;
import com.paywastex.repository.CustomerRepository;
import com.paywastex.repository.OurUsersRepo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
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
    private final CustomerRepository customerRepo;

    /* ---------------------------------------------------------
     * LOGIN
     * --------------------------------------------------------- */
    public ReqRes login(ReqRes in, HttpServletResponse response) {

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

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken",refresh)
                        .httpOnly(true)
//                        .secure(true)
                        .path("/auth/refresh")
                        .sameSite("Strict")
                        .maxAge(7*24*60*60)
                        .build();

        response.addHeader("Set-Cookie", refreshCookie.toString());
        resp.setStatusCode(200);
        resp.setMassage("Logged-in");
        resp.setToken(access);
        resp.setExpirationTime("5 min");
        resp.setRole(user.getRole());
        resp.setUserId(user.getId());
        return resp;
    }

    /* ---------------------------------------------------------
     * REFRESH TOKEN
     * --------------------------------------------------------- */
    public ReqRes refreshToken(String refreshToken, HttpServletResponse response) {

        ReqRes resp = new ReqRes();

        try {
            OurUsers user   = refreshSvc.validateAndRotate(refreshToken); // revokes old
            String access = jwt.generateToken(user);
            String newRefresh    = refreshSvc.issue(user);

            ResponseCookie cookie = ResponseCookie.from("refreshToken",newRefresh)
                            .httpOnly(true)
                                    .secure(false)
                                            .path("/auth/refresh")
                                                    .sameSite("Strict")
                                                            .maxAge(7*24*60*60)
                                                                    .build();
            response.addHeader("Set-Cookie", cookie.toString());
            resp.setStatusCode(200);
            resp.setMassage("Token refreshed");
    resp.setToken(access);
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

    public ReqRes registerCustomer(CustomerRegistration dto) {
        ReqRes response = new ReqRes();

        try {
            // 1. Check if user already exists
            if (usersRepo.existsByEmail(dto.getEmail())) {
                response.setStatusCode(409);
                response.setMassage("Email already exists");
                return response;
            }

            // 2. Create OurUsers entity
            OurUsers user = new OurUsers();
            user.setFullName(dto.getFullName());
            user.setEmail(dto.getEmail());
            user.setPassword(encoder.encode(dto.getPassword()));
            user.setNic(dto.getNic());
            user.setContactNo(dto.getContactNo());
            user.setAddress(dto.getAddress());
            user.setRole("CUSTOMER");
            user.setCreatedAt(new Date());

            usersRepo.save(user);

            // 3. Create Customer entity
            Customer customer = new Customer();
            customer.setUser(user);
            customer.setBusinessName(dto.getBusinessName());
            customer.setBusinessType(dto.getBusinessType());
            customer.setRegistrationNumber(dto.getRegistrationNumber());
            customer.setLocation(dto.getAddress());
            customer.setCity(dto.getCity());

            customerRepo.save(customer);

            // 4. Return success
            response.setStatusCode(201);
            response.setMassage("Customer registered successfully");
            response.setUserId(user.getId());
            response.setRole(user.getRole());
            return response;

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMassage("Registration failed: " + e.getMessage());
            return response;
        }
    }
}
