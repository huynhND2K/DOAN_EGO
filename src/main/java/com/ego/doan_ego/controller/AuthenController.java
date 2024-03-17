package com.ego.doan_ego.controller;

import com.ego.doan_ego.config.service.JwtAuthService;
import com.ego.doan_ego.repository.AccountRepository;
import com.ego.doan_ego.request.JwtRequest;
import com.ego.doan_ego.request.LoginRequest;
import com.ego.doan_ego.request.account.RegisterAccountRequest;
import com.ego.doan_ego.response.BaseResponse;
import com.ego.doan_ego.service.interfaceService.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthenController {

    private final JwtAuthService jwtAuthService;
    private final AccountService accountService;

    public AuthenController(JwtAuthService jwtAuthService, AccountService accountService) {
        this.jwtAuthService = jwtAuthService;
        this.accountService = accountService;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @Transactional(timeout = 230)
    public BaseResponse<?> login(@RequestBody  JwtRequest request) throws Exception {
        return jwtAuthService.createAuthenticationToken(request);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public BaseResponse<?> createAccount(@RequestBody RegisterAccountRequest request){
        return accountService.createAccount(request);
    }
}
