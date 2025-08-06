package com.paywastex.dto;

import com.paywastex.entity.auth.OurUsers;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveAllCustomerResponse {

    private String fullName;
    private String contactNo;
    private String businessName;
    private String registrationNumber;
    private Long zoneId;

}
