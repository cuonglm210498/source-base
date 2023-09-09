package com.lecuong.sourcebase.modal.request.rabbitmq;

import lombok.Data;
import lombok.ToString;

/**
 * @author CuongLM
 * @created 09/09/2023
 * @project source-base
 */
@Data
@ToString
public class NotificationRequest {

    private String funcCode;
    private String funcId;
    private String funcName;
    private String itemType;
    private String item;
    private String itemId;
    private String action;
    private String oldValue;
    private String newValue;
    private String receiver;
    private String actionBy;
    private String createdBy;
    private String description;
    private String tenantCode;
}
