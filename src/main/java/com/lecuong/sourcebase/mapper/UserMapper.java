package com.lecuong.sourcebase.mapper;

import com.lecuong.sourcebase.entity.Role;
import com.lecuong.sourcebase.entity.User;
import com.lecuong.sourcebase.modal.request.user.UserSaveRequest;
import com.lecuong.sourcebase.modal.response.UserResponse;
import com.lecuong.sourcebase.repository.RoleRepository;
import com.lecuong.sourcebase.util.AlgorithmSha;
import com.lecuong.sourcebase.util.BeanUtils;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Data
@Component
public class UserMapper {

    private final RoleRepository roleRepository;

    public User to(UserSaveRequest userSaveRequest) {
        User user = new User();
        BeanUtils.refine(userSaveRequest, user, BeanUtils::copyNonNull);
        user.setPassword(AlgorithmSha.hash(userSaveRequest.getPassword()));

        Set<Role> roles = new HashSet<>(roleRepository.findByIdIn(userSaveRequest.getIds()));
        user.setRoles(roles);

        return user;
    }

    public UserResponse to(User user) {
        UserResponse userResponse = new UserResponse();
        BeanUtils.refine(user, userResponse, BeanUtils::copyNonNull);
        return userResponse;
    }
}
