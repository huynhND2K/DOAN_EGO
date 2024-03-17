package com.ego.doan_ego.controller;

import com.ego.doan_ego.request.user.UpdateUserProfileRequest;
import com.ego.doan_ego.response.BaseResponse;
import com.ego.doan_ego.service.interfaceService.UserService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PutMapping("/update")
    public BaseResponse<?> updateUserProfile(UpdateUserProfileRequest request){
        return userService.updateUserProfile(request);
    }
}
