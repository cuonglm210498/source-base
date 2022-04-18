package com.lecuong.sourcebase.service;

import com.lecuong.sourcebase.modal.request.user.UserAuthRequest;
import com.lecuong.sourcebase.modal.request.user.UserFilterRequest;
import com.lecuong.sourcebase.modal.request.user.UserSaveRequest;
import com.lecuong.sourcebase.modal.request.user.UserUpdateRequest;
import com.lecuong.sourcebase.modal.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

    UserResponse auth(UserAuthRequest userAuthRequest);

    void save(UserSaveRequest userSaveRequest);

    void update(Long id, UserUpdateRequest userUpdateRequest);

    void delete(Long id);

    UserResponse findById(Long id);

    Page<UserResponse> getAll(Pageable pageable);

    Page<UserResponse> filter(UserFilterRequest userFilterRequest);
}
