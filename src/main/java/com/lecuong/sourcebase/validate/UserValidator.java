package com.lecuong.sourcebase.validate;

import com.lecuong.sourcebase.exception.BusinessException;
import com.lecuong.sourcebase.exception.StatusTemplate;
import com.lecuong.sourcebase.modal.request.user.UserAuthRequest;
import com.lecuong.sourcebase.modal.request.user.UserSaveRequest;
import com.lecuong.sourcebase.service.UserService;
import com.lecuong.sourcebase.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author CuongLM18
 * @created 29/03/2023 - 4:07 PM
 * @project source-base
 */
@Component
public class UserValidator {

    @Autowired
    private UserService userService;

    public UserSaveRequest validateUserSaveRequest(UserSaveRequest userCreateRequest) {
        return Validator.data(userCreateRequest)
                .validate(UserSaveRequest::getUserName, StringUtils::isBlank, () -> new BusinessException(StatusTemplate.USER_NAME_IS_EMPTY))
                .validate(UserSaveRequest::getUserName, userService::checkUserNameExist, () -> new BusinessException(StatusTemplate.USER_NAME_AVAILABLE))
                .validate(UserSaveRequest::getPassword, StringUtils::isBlank, () -> new BusinessException(StatusTemplate.PASSWORD_IS_EMPTY))
                .validate(UserSaveRequest::getPassword, StringUtils::verifyPassword, () -> new BusinessException(StatusTemplate.PASSWORD_INVALIDATE))
                .validate(UserSaveRequest::getEmail, StringUtils::verifyEmail, () -> new BusinessException(StatusTemplate.EMAIL_INVALIDATE))
                .validate(UserSaveRequest::getEmail, userService::checkEmailExist, () -> new BusinessException(StatusTemplate.EMAIL_AVAILABLE))
                .validate(UserSaveRequest::getEmail, StringUtils::isBlank, () -> new BusinessException(StatusTemplate.EMAIL_INVALIDATE))
                .validate(UserSaveRequest::getPhone, StringUtils::verifyPhoneNumber, () -> new BusinessException(StatusTemplate.PHONE_NUMBER_INVALIDATE))
                .validate(UserSaveRequest::getPhone, userService::checkPhoneExits, () -> new BusinessException(StatusTemplate.PHONE_AVAILABLE))
                .getData();
    }

    public UserAuthRequest validateUserAuthRequest(UserAuthRequest userAuthRequest) {
        return Validator.data(userAuthRequest)
                .validate(UserAuthRequest::getUserName, StringUtils::isBlank, () -> new BusinessException(StatusTemplate.USER_NAME_IS_EMPTY))
                .validate(UserAuthRequest::getPassword, StringUtils::isBlank, () -> new BusinessException(StatusTemplate.PASSWORD_IS_EMPTY))
                .validate(UserAuthRequest::getPassword, StringUtils::verifyPassword, () -> new BusinessException(StatusTemplate.PASSWORD_INVALIDATE))
                .getData();
    }
}
