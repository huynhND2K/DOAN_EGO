package com.ego.doan_ego.service.impl;

import com.ego.doan_ego.constant.CommonMessage;
import com.ego.doan_ego.entities.AccountDao;
import com.ego.doan_ego.entities.UserDao;
import com.ego.doan_ego.repository.AccountRepository;
import com.ego.doan_ego.repository.UserRepository;
import com.ego.doan_ego.request.account.RegisterAccountRequest;
import com.ego.doan_ego.response.BaseResponse;
import com.ego.doan_ego.service.interfaceService.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Transactional
    @Override
    public BaseResponse<?> createAccount(RegisterAccountRequest request) {
        AccountDao accountDao = accountRepository.getAccountByUsername(request.getUsername());
        if (accountDao != null){
            return new BaseResponse<>(CommonMessage.USER_EXISTED);
        }
        UserDao userDao = new UserDao();
        userDao.setFullName(request.getFullName());
        userDao.setEmail(request.getEmail());
        userDao.setPhone(request.getPhone());
        userRepository.save(userDao);

        AccountDao newAccount = new AccountDao();
        newAccount.setUsername(request.getUsername());
        log.info("password: {}", request.getPassword());
        newAccount.setPassword(bcryptEncoder.encode(request.getPassword()));
        newAccount.setUserId(userDao.getId());
        newAccount.setUserType(request.getTypeUser());
        accountRepository.save(newAccount);

        return new BaseResponse<>(CommonMessage.SUCCESS,"Tạo tài khoản");
    }
}
