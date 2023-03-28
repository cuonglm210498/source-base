package com.lecuong.sourcebase.modal.request.employee;

import com.lecuong.sourcebase.constant.PositionEnum;
import lombok.Data;

/**
 * @author CuongLM18
 * @created 28/03/2023 - 10:30 AM
 * @project source-base
 */
@Data
public class EmployeeRequest {

    private String name;
    private PositionEnum positionEnum;
}
