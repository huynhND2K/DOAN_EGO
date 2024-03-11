package com.ego.doan_ego.config.service.impl;

import com.ego.doan_ego.config.JwtTokenUtil;
import com.ego.doan_ego.config.service.JwtAuthService;
import com.ego.doan_ego.constant.CommonMessage;
import com.ego.doan_ego.entities.AccountDao;
import com.ego.doan_ego.entities.UserDao;
import com.ego.doan_ego.repository.AccountRepository;
import com.ego.doan_ego.repository.UserRepository;
import com.ego.doan_ego.request.JwtRequest;
import com.ego.doan_ego.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JwtAuthServiceImpl implements UserDetailsService, JwtAuthService {
    @Autowired
    private PasswordEncoder bcryptEncoder;
//    @Autowired
//    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${jwt.secret}")
    private String secret;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public BaseResponse<?> validateToken(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public BaseResponse<?> createAuthenticationToken(JwtRequest authenticationRequest) throws Exception {
        return null;
    }

    @Override
    public BaseResponse<?> save(JwtRequest user) {
        return null;
    }

    @Override
    public BaseResponse<?> logout(String token) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountDao account = accountRepository.getAccountByUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException(CommonMessage.USER_NOT_FOUND.message);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        UserDao userDao = userRepository.getUserDaoById(account.getUserId());
        if (account.getUserType() != null) {
            List<String> permissions = new ArrayList<>();
            permissions.add(account.getUserType().toString());

            if (permissions != null && !permissions.isEmpty()) {
                authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            }
        }

        return new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(),
                authorities);

    }
}
