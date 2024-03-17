package com.ego.doan_ego.service.interfaceService;

import com.ego.doan_ego.request.CreateGroupRequest;
import com.ego.doan_ego.request.UpdateGroupRequest;
import com.ego.doan_ego.request.group.AddListUserGroupRequest;
import com.ego.doan_ego.response.BaseResponse;


public interface GroupService {
    BaseResponse<?> createGroup(CreateGroupRequest request);
    BaseResponse<?> updateGroup(UpdateGroupRequest request);
    BaseResponse<?> addUsers(AddListUserGroupRequest request);
}
