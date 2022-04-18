package com.lecuong.sourcebase.modal.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserResponse {

    private Long id;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String fullName;
    private LocalDate dateOfBirth;
    private String avatar;
    private transient List<String> roleName;
}
