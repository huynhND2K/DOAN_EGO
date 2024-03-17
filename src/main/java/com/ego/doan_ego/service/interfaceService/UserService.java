package com.ego.doan_ego.service.interfaceService;

import com.ego.doan_ego.request.user.UpdateUserProfileRequest;
import com.ego.doan_ego.response.BaseResponse;

public interface UserService {
    public BaseResponse<?> updateUserProfile(UpdateUserProfileRequest request);
}
