package com.ego.doan_ego.request.account;

import lombok.Data;

@Data
public class RegisterAccountRequest {
    private String username;
    private String password;
    private String fullName;
    private String phone;
    private String email;
    private Integer typeUser;
}
