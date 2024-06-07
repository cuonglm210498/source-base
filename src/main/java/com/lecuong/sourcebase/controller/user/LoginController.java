package com.lecuong.sourcebase.controller.user;

import com.lecuong.sourcebase.modal.request.user.UserAuthRequest;
import com.lecuong.sourcebase.modal.response.BaseResponse;
import com.lecuong.sourcebase.modal.response.user.UserResponse;
import com.lecuong.sourcebase.security.jwt.TokenProducer;
import com.lecuong.sourcebase.security.jwt.model.JwtPayLoad;
import com.lecuong.sourcebase.service.UserService;
import com.lecuong.sourcebase.validate.UserValidator;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Data
@RestController
@RequestMapping("/api/v1/user/login")
public class LoginController {

    private final UserValidator userValidator;
    private final UserService userService;
    private final TokenProducer tokenProducer;

    @PostMapping
    public ResponseEntity<BaseResponse<String>> login(@RequestBody @Valid UserAuthRequest userAuthRequest) {

        //Validate userAuthRequest
//        userValidator.validateUserAuthRequest(userAuthRequest);

        UserResponse userResponse = userService.auth(userAuthRequest);
        JwtPayLoad jwtPayload = createPayload(userResponse);
        String token = tokenProducer.token(jwtPayload);
        return ResponseEntity.ok(BaseResponse.ofSuccess(token));
    }

    private JwtPayLoad createPayload(UserResponse userResponse){
        JwtPayLoad jwtPayload = new JwtPayLoad();
        jwtPayload.setUserName(userResponse.getUserName());
        jwtPayload.setId(userResponse.getId());
        //String role = user.getRoles().stream().map(Role::getName).collect(Collectors.joining(","));
        String role = String.join(",", userResponse.getRoleName());
        jwtPayload.setRole(role);

        return jwtPayload;
    }

}
