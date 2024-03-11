package com.ego.doan_ego.request;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UpdateGroupRequest {
    @Column(name = "group_id")
    private Long groupId;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "user_id")
    private Long userId;
}
