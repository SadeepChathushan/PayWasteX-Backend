package com.paywastex.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.paywastex.entity.auth.OurUsers;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqRes {
    private int statusCode;
    private String error;
    private String massage;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String role;
    private int userId;
    private String fullName;
    private String email;
    private String address;
    private String contactNo;
    private String password;
    private String nic;
    private Byte status;
    private Date createdAt;
    private Date dob;
    private String gender;
    private List<OurUsers> ourUsersList;

    public void setOurUsers(OurUsers user) {
        this.ourUsersList = List.of(user);
    }


}
