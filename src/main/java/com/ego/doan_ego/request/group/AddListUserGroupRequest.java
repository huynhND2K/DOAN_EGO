package com.ego.doan_ego.request.group;

import lombok.Data;

import java.util.List;

@Data
public class AddListUserGroupRequest {
    private List<String> listUserName;
    private Long groupId;
}
