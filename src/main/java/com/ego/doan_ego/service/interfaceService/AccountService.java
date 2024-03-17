package com.ego.doan_ego.service.interfaceService;

import com.ego.doan_ego.request.account.RegisterAccountRequest;
import com.ego.doan_ego.response.BaseResponse;

public interface AccountService {
    BaseResponse<?> createAccount(RegisterAccountRequest request);
}
