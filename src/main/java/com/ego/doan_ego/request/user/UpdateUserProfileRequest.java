package com.ego.doan_ego.request.user;

import lombok.Data;

@Data
public class UpdateUserProfileRequest {
    private Long id;
    private String fullName;
    private String phone;
    private String email;
}
