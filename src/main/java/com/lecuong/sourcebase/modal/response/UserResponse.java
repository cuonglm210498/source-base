package com.lecuong.sourcebase.modal.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lecuong.sourcebase.common.DateTimeCommon;
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
//    private List<String> url;

    @JsonFormat(pattern = DateTimeCommon.DateTimeFormat.DD_MM_YYYY, timezone = DateTimeCommon.DateTimeFormat.TIME_ZONE)
    private LocalDate dateOfBirth;

    private String avatar;
    private transient List<String> roleName;
}
