package com.paywastex.service;

import com.paywastex.dto.GetAllUserResponse;

import java.util.List;

public interface AdminService {
    List<GetAllUserResponse> getAllUsers();
    void deleteUserById(Integer id);
}
