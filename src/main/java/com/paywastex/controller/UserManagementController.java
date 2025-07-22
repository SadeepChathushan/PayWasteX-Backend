package com.paywastex.controller;

import com.paywastex.dto.ReqRes;
import com.paywastex.service.UsersManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ReqRes> login(@RequestBody ReqRes req) {
        return ResponseEntity.ok(usersManagementService.login(req));
    }



    @PostMapping("/auth/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes req) {
        return ResponseEntity.ok(usersManagementService.refreshToken(req));
    }

}
