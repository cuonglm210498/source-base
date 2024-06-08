package com.lecuong.sourcebase.schedule.quartzv1.model;

import lombok.Data;

/**
 * @author CuongLM
 * @created 08/06/2024 - 10:44
 * @project source-base
 */
@Data
public class TaskDefinition {

    private String cronExpression;
    private String actionType;
    private String data;
}
