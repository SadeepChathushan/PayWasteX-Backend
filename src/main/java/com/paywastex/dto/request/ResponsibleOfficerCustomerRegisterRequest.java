package com.paywastex.dto.request;

import com.paywastex.entity.auth.OurUsers;
import com.paywastex.entity.customer.Zone;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Data
public class ResponsibleOfficerCustomerRegisterRequest {

    private String role;
    private String fullName;
    private String email;
    private String address;
    private String contactNo;
    private String password;
    private String nic;


    private OurUsers user;
    private String businessName;
    private String businessType;
    private String registrationNumber;
    private String city;
    private Long zoneId;
}
