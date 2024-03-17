package com.ego.doan_ego.config.service;

import com.ego.doan_ego.request.JwtRequest;
import com.ego.doan_ego.request.account.RegisterAccountRequest;
import com.ego.doan_ego.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface JwtAuthService {

    BaseResponse<?> validateToken(HttpServletRequest request, HttpServletResponse response);

    BaseResponse<?> createAuthenticationToken(JwtRequest authenticationRequest) throws Exception;

    BaseResponse<?> save(JwtRequest user);

    BaseResponse<?> logout(String token);

}
