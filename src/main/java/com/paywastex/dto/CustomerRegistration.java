package com.paywastex.dto;

import lombok.Data;

@Data
public class CustomerRegistration {
    // Personal Info
    private String fullName;
    private String email;
    private String password;
    private String nic;
    private String contactNo;

    // Shop Info
    private String businessName;
    private String businessType;
    private String registrationNumber;
    private String address;
    private String city;

}
