package com.paywastex.controller;

import com.paywastex.dto.request.CustomerRegistration;
import com.paywastex.dto.ReqRes;
import com.paywastex.service.UsersManagementService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserManagementController {

    @Autowired
    private UsersManagementService usersManagementService;

    @PostMapping("/auth/user-register")
    public ResponseEntity<ReqRes> midwifeRegister(@RequestBody ReqRes req) {
        return ResponseEntity.ok(usersManagementService.userRegister(req));
    }


    @PostMapping("/auth/login")
    public ResponseEntity<ReqRes> login(@RequestBody ReqRes req, HttpServletResponse response) {
        return ResponseEntity.ok(usersManagementService.login(req, response));
    }



    @PostMapping("/auth/refresh")
    public ResponseEntity<ReqRes> refreshToken(@CookieValue(value = "refreshToken", required = false) String refreshToken,HttpServletResponse response) {
        if (refreshToken == null){
            ReqRes err = new ReqRes();
            err.setStatusCode(401);
            err.setMassage("Missing refresh token");
            return ResponseEntity.status(401).body(err);
        }
        ReqRes result = usersManagementService.refreshToken(refreshToken,response);
        return ResponseEntity.status(result.getStatusCode()).body(result);
    }

    @PostMapping("/auth/customer-register")
    public ResponseEntity<ReqRes> registerCustomer(@RequestBody CustomerRegistration dto) {
        ReqRes res = usersManagementService.registerCustomer(dto);
        return ResponseEntity.status(res.getStatusCode()).body(res);
    }


}
