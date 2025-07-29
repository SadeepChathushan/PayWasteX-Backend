package com.paywastex.controller;

import com.paywastex.dto.GetAllUserResponse;
import com.paywastex.dto.request.AdminUserEditRequest;
import com.paywastex.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam Integer id){
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestParam Integer id,@RequestBody AdminUserEditRequest request){
        userService.updateUserById(id, request);
        return ResponseEntity.ok("User updated successfully");
    }
}
