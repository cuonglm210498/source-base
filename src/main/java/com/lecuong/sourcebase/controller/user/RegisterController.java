package com.lecuong.sourcebase.controller.user;

import com.lecuong.sourcebase.modal.request.user.UserSaveRequest;
import com.lecuong.sourcebase.modal.response.BaseResponse;
import com.lecuong.sourcebase.service.UserService;
import com.lecuong.sourcebase.validate.UserValidator;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@RequestMapping("/api/v1/user/register")
public class RegisterController {

    private final UserValidator userValidator;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> save(@RequestBody UserSaveRequest userSaveRequest) {
        //validate userSaveRequest
        userValidator.validateUserSaveRequest(userSaveRequest);

        userService.save(userSaveRequest);
        return ResponseEntity.ok(BaseResponse.ofSuccess(null));
    }
}
