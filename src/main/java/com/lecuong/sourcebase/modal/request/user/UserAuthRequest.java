package com.lecuong.sourcebase.modal.request.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserAuthRequest {

    @NotBlank(message = "username is mandatory")
    private String userName;

    @Size(min = 8, message = "Password should have at least 8 characters")
    private String password;
}
