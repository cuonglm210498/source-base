package com.lecuong.sourcebase.service.impl;

import com.lecuong.sourcebase.entity.Role;
import com.lecuong.sourcebase.entity.User;
import com.lecuong.sourcebase.exception.BusinessException;
import com.lecuong.sourcebase.exception.StatusTemplate;
import com.lecuong.sourcebase.mapper.UserMapper;
import com.lecuong.sourcebase.modal.request.user.*;
import com.lecuong.sourcebase.modal.response.UserResponse;
import com.lecuong.sourcebase.repository.UserRepository;
import com.lecuong.sourcebase.service.UserService;
import com.lecuong.sourcebase.specification.UserSpecification;
import com.lecuong.sourcebase.util.AlgorithmSha;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserResponse auth(UserAuthRequest userAuthRequest) {
        String password = AlgorithmSha.hash(userAuthRequest.getPassword());

        Optional<User> user = userRepository.findByUserNameAndPassword(userAuthRequest.getUserName(), password);
        user.orElseThrow(() -> new BusinessException(StatusTemplate.USER_NOT_FOUND));

        UserResponse userResponse = userMapper.to(user.get());

        List<String> roles = user.get().getRoles().stream().map(Role::getName).collect(Collectors.toList());
        userResponse.setRoleName(roles);

        return userResponse;
    }

    @Override
    public void save(UserSaveRequest userSaveRequest) {
        User user = userMapper.to(userSaveRequest);
        userRepository.save(user);
    }

    @Override
    public void update(Long id, UserUpdateRequest userUpdateRequest) {
        userRepository.findById(id)
                .map(user -> {
                    user.setPassword(AlgorithmSha.hash(userUpdateRequest.getPassword()));
                    user.setEmail(userUpdateRequest.getEmail());
                    user.setPhone(userUpdateRequest.getPhone());
                    user.setAddress(userUpdateRequest.getAddress());
                    user.setFullName(userUpdateRequest.getFullName());
                    user.setDateOfBirth(userUpdateRequest.getDateOfBirth());
                    user.setAvatar(userUpdateRequest.getAvatar());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new BusinessException(StatusTemplate.USER_NOT_FOUND));
    }

    @Override
    public void delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        user.orElseThrow(() -> new BusinessException(StatusTemplate.USER_NOT_FOUND));

        userRepository.deleteById(id);
    }

    @Override
    public UserResponse findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        user.orElseThrow(() -> new BusinessException(StatusTemplate.USER_NOT_FOUND));

        return userMapper.to(user.get());
    }

    @Override
    public Page<UserResponse> getAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable.previousOrFirst());
        return users.map(userMapper::to);
    }

    @Override
    public Page<UserResponse> filter(UserFilterRequest userFilterRequest) {

        PageRequest pageRequest = PageRequest.of(userFilterRequest.getPageIndex(), userFilterRequest.getPageSize());
        Page<User> users = userRepository.findAll(UserSpecification.filter(userFilterRequest), pageRequest.previousOrFirst());
        return users.map(userMapper::to);
    }

    @Override
    public Page<UserResponse> filter(UserFilterWithListBlogRequest userFilterWithListBlogRequest) {
        PageRequest pageRequest = PageRequest.of(userFilterWithListBlogRequest.getPageIndex(), userFilterWithListBlogRequest.getPageSize());
        Page<User> users = userRepository.findAll(UserSpecification.filter(userFilterWithListBlogRequest), pageRequest.previousOrFirst());
        return users.map(userMapper::to);
    }

    @Override
    public boolean checkEmailExist(String email) {
        return userRepository.countAllByEmail(email) > 0;
    }

    @Override
    public boolean checkPhoneExits(String phone) {
        return userRepository.countAllByPhone(phone) > 0;
    }

    @Override
    public boolean checkUserNameExist(String userName) {
        return userRepository.countAllByUserName(userName) > 0;
    }

}
