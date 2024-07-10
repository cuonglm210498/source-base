package com.lecuong.sourcebase.modal.response.refreshtoken;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lecuong.sourcebase.common.DateTimeCommon;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author CuongLM
 * @created 11/07/2024 - 00:50
 * @project source-base
 */
@Data
public class RefreshTokenResponse {

    private Long id;

    private Long userId;

    private String userName;

    private String refreshToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresDate;

    @JsonFormat(pattern = DateTimeCommon.DateTimeFormat.DD_MM_YYYY, timezone = DateTimeCommon.DateTimeFormat.TIME_ZONE)
    private LocalDate createdDate;

    private String createdBy;
    @JsonFormat(pattern = DateTimeCommon.DateTimeFormat.DD_MM_YYYY, timezone = DateTimeCommon.DateTimeFormat.TIME_ZONE)
    private LocalDate modifiedDate;

    private String modifiedBy;

    private Boolean isActive;

    private Boolean isDeleted;
}
