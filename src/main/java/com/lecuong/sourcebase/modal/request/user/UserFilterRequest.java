package com.lecuong.sourcebase.modal.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lecuong.sourcebase.common.DateTimeCommon;
import com.lecuong.sourcebase.modal.request.BaseRequest;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class UserFilterRequest extends BaseRequest {

    private Long roleId;
    private Long blogId;
    private List<Long> userIds;
    private String url;
    private String userName;
    private String address;
    private String email;
    @JsonFormat(pattern = DateTimeCommon.DateTimeFormat.DD_MM_YYYY, timezone = DateTimeCommon.DateTimeFormat.TIME_ZONE)
    private Date dateOfBirthFrom;

    @JsonFormat(pattern = DateTimeCommon.DateTimeFormat.DD_MM_YYYY, timezone = DateTimeCommon.DateTimeFormat.TIME_ZONE)
    private Date dateOfBirthTo;
}
