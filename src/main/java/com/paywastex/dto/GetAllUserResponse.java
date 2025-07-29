package com.paywastex.dto;

import com.paywastex.entity.OurUsers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllUserResponse {

    private int id;
    private String role;
    private String fullName;
    private String email;
    private String nic;
    private String contactNo;
    private boolean accountNonLocked;


}
