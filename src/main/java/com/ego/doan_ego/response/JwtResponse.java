package com.ego.doan_ego.response;

import com.ego.doan_ego.entities.UserDao;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private List<String> permission;
    private UserDao userDao;
}
