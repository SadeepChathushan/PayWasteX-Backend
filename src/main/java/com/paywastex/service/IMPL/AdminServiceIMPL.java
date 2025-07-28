package com.paywastex.service.IMPL;

import com.paywastex.dto.GetAllUserResponse;
import com.paywastex.entity.OurUsers;
import com.paywastex.repository.OurUsersRepo;
import com.paywastex.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceIMPL implements AdminService {
    @Autowired
    private OurUsersRepo ourUsersRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<GetAllUserResponse> getAllUsers() {
        List<OurUsers> users = ourUsersRepo.findAll();

        return users.stream()
                .map(user -> modelMapper.map (user, GetAllUserResponse.class))
                .collect(Collectors.toList());
    }
    @Override
    public void deleteUserById(Integer id) {
        OurUsers user = ourUsersRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        ourUsersRepo.delete(user);
    }
}
