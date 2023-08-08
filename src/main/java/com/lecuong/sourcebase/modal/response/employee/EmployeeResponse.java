package com.lecuong.sourcebase.modal.response.employee;

import com.lecuong.sourcebase.constant.PositionEnum;
import lombok.Data;

/**
 * @author CuongLM18
 * @created 28/03/2023 - 10:37 AM
 * @project source-base
 */
@Data
public class EmployeeResponse {

    private Long id;
    private String name;
    private PositionEnum positionEnum;
}
