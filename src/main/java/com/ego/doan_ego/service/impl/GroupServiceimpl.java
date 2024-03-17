package com.ego.doan_ego.service.impl;

import com.ego.doan_ego.constant.AccountTypeContants;
import com.ego.doan_ego.constant.CommonMessage;
import com.ego.doan_ego.entities.AccountDao;
import com.ego.doan_ego.entities.GroupDao;
import com.ego.doan_ego.entities.GroupUserDao;
import com.ego.doan_ego.entities.UserDao;
import com.ego.doan_ego.repository.AccountRepository;
import com.ego.doan_ego.repository.GroupRepository;
import com.ego.doan_ego.repository.GroupUserRepository;
import com.ego.doan_ego.repository.UserRepository;
import com.ego.doan_ego.request.CreateGroupRequest;
import com.ego.doan_ego.request.UpdateGroupRequest;
import com.ego.doan_ego.request.group.AddListUserGroupRequest;
import com.ego.doan_ego.response.BaseResponse;
import com.ego.doan_ego.service.interfaceService.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GroupServiceimpl implements GroupService {
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupUserRepository groupUserRepository;

    @Override
    public BaseResponse<?> createGroup(CreateGroupRequest request) {
        try {
            UserDao userDao = userRepository.getUserDaoById(request.getUserId());
            if (userDao == null) {
                return new BaseResponse<>(CommonMessage.USER_NOT_FOUND);
            }
            AccountDao accountDao = accountRepository.getAccountByUsername(request.getUsername());
            if (accountDao == null) {
                return new BaseResponse<>(CommonMessage.USER_NOT_FOUND);
            }
            if (accountDao.getUserType() != AccountTypeContants.TEACHER.value) {
                return new BaseResponse<>(CommonMessage.USER_DONT_HAVE_PERMISSION);
            }
            List<String> groupDaoList = groupRepository.getListGroupName(userDao.getId());
            if (groupDaoList != null) {
               List<String> listGroupExist = groupDaoList.parallelStream().
                       filter(g -> g.equals(request.getGroupName())).collect(Collectors.toList());
               if (listGroupExist != null){
                   return new BaseResponse<>(CommonMessage.OBJECT_EXIST, "Tên nhóm");
               }
            }
            GroupDao groupDao = new GroupDao();
            groupDao.setGroupName(request.getGroupName());
            groupDao.setCreatedBy(userDao.getId());
            groupRepository.save(groupDao);
            return new BaseResponse<>(CommonMessage.SUCCESS, groupDao);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Create group failed: {}", e.getMessage());
        }
        return new BaseResponse<>(CommonMessage.FAILED, new GroupDao());
    }
    @Override
    public BaseResponse<?> updateGroup(UpdateGroupRequest request) {
        try{
            GroupDao groupDao = groupRepository.getGroupDaoByGroupId(request.getGroupId());
            if (Objects.equals(groupDao.getCreatedBy(), request.getUserId())){
                groupDao.setGroupName(request.getGroupName());
                List<String> listGroupCreatedByUser = groupRepository.getListGroupName(request.getUserId());
                if (listGroupCreatedByUser != null){
                    List<String> listGroupExist = listGroupCreatedByUser.parallelStream().
                            filter(g -> g.equals(request.getGroupName())).collect(Collectors.toList());
                    if (listGroupExist != null){
                        return new BaseResponse<>(CommonMessage.OBJECT_EXIST, "Tên nhóm");
                    }
                }
                groupRepository.save(groupDao);
                return new BaseResponse<>(CommonMessage.SUCCESS, "Cập nhật thông tin group");
            }
            return new BaseResponse<>(CommonMessage.FAILED, "Cập nhật thông tin group");
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error update group: {}", e.getMessage());
        }
        return new BaseResponse<>(CommonMessage.FAILED, "Cập nhật thông tin group");
    }

    @Override
    public BaseResponse<?> addUsers(AddListUserGroupRequest request) {
        List<Long> listUserId = accountRepository.getListAccountIdByUsername(request.getListUserName());
        Optional<GroupDao> groupDaoExist = groupRepository.findById(request.getGroupId());
        GroupDao groupDao = groupDaoExist.orElse(null);
        if(groupDao == null){
            return new BaseResponse<>(CommonMessage.GROUP_DOES_NOT_EXIST);
        }
        List<GroupUserDao> groupUserDaoList = groupUserRepository.getGroupUserDaoByGroupId(request.getGroupId());
        if(groupUserDaoList == null){
            List<GroupUserDao> newGroupUserDaoList = new ArrayList<>();
            listUserId.parallelStream().forEach(
                    u -> {
                        GroupUserDao newGroupUser = new GroupUserDao();
                        newGroupUser.setGroupId(request.getGroupId());
                        newGroupUser.setUserId(u);
                        newGroupUser.setJoinDate(System.currentTimeMillis());
                        newGroupUserDaoList.add(newGroupUser);
                    }
            );
            groupUserRepository.saveAll(newGroupUserDaoList);
            return new BaseResponse<>(CommonMessage.SUCCESS);
        }else{
            List<GroupUserDao> groupUser = groupUserDaoList.parallelStream()
                    .filter(e -> !listUserId.contains(e.getUserId()))
                    .collect(Collectors.toList());
            groupUserRepository.saveAll(groupUser);
            return new BaseResponse<>(CommonMessage.SUCCESS);
        }
    }


}
