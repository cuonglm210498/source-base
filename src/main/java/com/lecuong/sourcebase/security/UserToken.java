package com.lecuong.sourcebase.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserToken {

    private Long id;
    private String userName;
    @JsonIgnore
    private String password;
    private String email;
    private String phone;
    private String address;
    private String fullName;
    private List<String> roles;
}
