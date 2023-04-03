package com.lecuong.sourcebase.service.impl;

import com.lecuong.sourcebase.common.MessageCommon;
import com.lecuong.sourcebase.constant.MessageConstant;
import com.lecuong.sourcebase.entity.Role;
import com.lecuong.sourcebase.entity.User;
import com.lecuong.sourcebase.exception.BusinessException;
import com.lecuong.sourcebase.exception.StatusResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
public class UserServiceImpl implements UserService {

    private final MessageCommon messageCommon;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserResponse auth(UserAuthRequest userAuthRequest) {
        String password = AlgorithmSha.hash(userAuthRequest.getPassword());

        Optional<User> user = userRepository.findByUserNameAndPassword(userAuthRequest.getUserName(), password);

        return this.checkUserExist(user);
    }

    @Override
    public void save(UserSaveRequest userSaveRequest) {
        User user = userMapper.to(userSaveRequest);
        userRepository.save(user);
    }

    @Override
    public void update(Long id, UserUpdateRequest userUpdateRequest) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setPassword(AlgorithmSha.hash(userUpdateRequest.getPassword()));
            user.get().setEmail(userUpdateRequest.getEmail());
            user.get().setPhone(userUpdateRequest.getPhone());
            user.get().setAddress(userUpdateRequest.getAddress());
            user.get().setFullName(userUpdateRequest.getFullName());
            user.get().setDateOfBirth(userUpdateRequest.getDateOfBirth());
            user.get().setAvatar(userUpdateRequest.getAvatar());
            userRepository.save(user.get());
        } else {
            throw new BusinessException(StatusTemplate.USER_NOT_FOUND);
        }
    }

    @Override
    public void delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new BusinessException(StatusTemplate.USER_NOT_FOUND);
        }
    }

    @Override
    public UserResponse findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return this.checkUserExist(user);
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

    @Override
    public String getMessage(UserSaveRequest userSaveRequest) {
        if (userSaveRequest.getUserName() == null) {
            throw new BusinessException(
                    new StatusResponse(
                            MessageConstant.ERROR_NOT_FOUND,
                            messageCommon.getMessage(MessageConstant.ERROR_NOT_FOUND),
                            HttpStatus.NOT_FOUND));
        }
        return "Save successfully";
    }

    private UserResponse checkUserExist(Optional<User> user) {
        if (user.isPresent()) {
            UserResponse userResponse = userMapper.to(user.get());

            List<String> roles = user.get().getRoles().stream().map(Role::getName).collect(Collectors.toList());
            userResponse.setRoleName(roles);

            return userResponse;
        } else {
            throw new BusinessException(StatusTemplate.USER_NOT_FOUND);
        }
    }
}
