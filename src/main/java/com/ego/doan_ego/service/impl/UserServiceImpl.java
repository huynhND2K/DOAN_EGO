package com.ego.doan_ego.service.impl;

import com.ego.doan_ego.constant.CommonMessage;
import com.ego.doan_ego.entities.UserDao;
import com.ego.doan_ego.repository.UserRepository;
import com.ego.doan_ego.request.user.UpdateUserProfileRequest;
import com.ego.doan_ego.response.BaseResponse;
import com.ego.doan_ego.service.interfaceService.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public BaseResponse<?> updateUserProfile(UpdateUserProfileRequest request) {
        Optional<UserDao> userDao = userRepository.findById(request.getId());
        UserDao user = userDao.orElse(null);
        if(user == null){
            return new BaseResponse<>(CommonMessage.USER_NOT_FOUND);
        }
        userDao.get().setPhone(request.getPhone());
        userDao.get().setEmail(request.getEmail());
        userDao.get().setFullName(request.getFullName());
        userRepository.save(userDao.get());
        return new BaseResponse<>(CommonMessage.SUCCESS.code, "Cập nhật profile thành công", userDao.get());
    }


}
