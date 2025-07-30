package com.paywastex.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserEditRequest {

    private String fullName;
    private String email;
    private String nic;
    private String contactNo;

}
