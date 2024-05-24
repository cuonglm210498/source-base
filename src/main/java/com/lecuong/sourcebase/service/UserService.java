package com.lecuong.sourcebase.service;

import com.lecuong.sourcebase.modal.request.user.*;
import com.lecuong.sourcebase.modal.response.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface UserService {

    UserResponse auth(UserAuthRequest userAuthRequest);

    void save(UserSaveRequest userSaveRequest);

    void update(Long id, UserUpdateRequest userUpdateRequest);

    void delete(Long id);

    UserResponse findById(Long id);

    Page<UserResponse> getAll(Pageable pageable);

    Page<UserResponse> filter(UserFilterRequest userFilterRequest);

    Page<UserResponse> filter(UserFilterWithListBlogRequest userFilterWithListBlogRequest);

    boolean checkEmailExist(String email);

    boolean checkPhoneExits(String phone);

    boolean checkUserNameExist(String userName);

    String getMessage(UserSaveRequest userSaveRequest);

    List<UserResponse> getAll();

    void saveBatchProcess(List<UserSaveRequest> userSaveRequests);

    List<UserResponse> getAllUserUsingBatchProcess();

    List<UserResponse> getAllUserUsingBatchProcessV2();
}
