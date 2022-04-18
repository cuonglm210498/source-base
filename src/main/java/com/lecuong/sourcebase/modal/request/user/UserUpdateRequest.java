package com.lecuong.sourcebase.modal.request.user;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateRequest {

    private String password;
    private String email;
    private String phone;
    private String address;
    private String fullName;
    private LocalDate dateOfBirth;
    private String avatar;
}
