package com.lecuong.sourcebase.modal.response.apikey;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lecuong.sourcebase.common.DateTimeCommon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author CuongLM
 * @created 20/05/2024 - 20:30
 * @project source-base
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiKeyResponse {

    private long id;

    @JsonFormat(pattern = DateTimeCommon.DateTimeFormat.DD_MM_YYYY, timezone = DateTimeCommon.DateTimeFormat.TIME_ZONE)
    private LocalDate createdDate;

    private String createdBy;

    @JsonFormat(pattern = DateTimeCommon.DateTimeFormat.DD_MM_YYYY, timezone = DateTimeCommon.DateTimeFormat.TIME_ZONE)
    private LocalDate modifiedDate;

    private String modifiedBy;

    private Boolean isActive;

    private Boolean isDeleted;

    private String key;

    private String description;
}
