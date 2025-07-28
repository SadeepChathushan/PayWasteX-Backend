package com.paywastex.controller;

import com.paywastex.dto.GetAllUserResponse;
import com.paywastex.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    private AdminService userService;

    @GetMapping("/all")
    public ResponseEntity<List<GetAllUserResponse>> getAllUsers() {
        List<GetAllUserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
