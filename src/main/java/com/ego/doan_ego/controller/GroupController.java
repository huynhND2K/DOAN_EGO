package com.ego.doan_ego.controller;

import com.ego.doan_ego.request.CreateGroupRequest;
import com.ego.doan_ego.request.UpdateGroupRequest;
import com.ego.doan_ego.request.group.AddListUserGroupRequest;
import com.ego.doan_ego.response.BaseResponse;
import com.ego.doan_ego.service.interfaceService.GroupService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/group")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }
    @PostMapping("/create")
    public BaseResponse<?> createGroup(CreateGroupRequest request){
        return groupService.createGroup(request);
    }
    @PutMapping("/update")
    public BaseResponse<?> updateGroup(UpdateGroupRequest request){
        return groupService.updateGroup(request);
    }

    @PostMapping("/add_users")
    public BaseResponse<?> addListMemberIntoGroup(AddListUserGroupRequest request){
        return groupService.addUsers(request);
    }
}
