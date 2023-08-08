package com.lecuong.sourcebase.repository.custom;

import com.lecuong.sourcebase.modal.request.user.UserFilterRequest;
import com.lecuong.sourcebase.modal.response.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author CuongLM18
 * @created 06/06/2023 - 3:16 PM
 * @project source-base
 */
public interface UserCustomRepository {

    Page<UserResponse> getAllUser(Pageable pageable);

    Page<UserResponse> filterUser(UserFilterRequest filter, Pageable pageable);
}
