package com.paywastex.service;

import com.paywastex.dto.GetAllUserResponse;
import com.paywastex.dto.request.AdminUserEditRequest;

import java.util.List;

public interface AdminService {
    List<GetAllUserResponse> getAllUsers();
    void deleteUserById(Integer id);
    void updateUserById(Integer id, AdminUserEditRequest request);
}
