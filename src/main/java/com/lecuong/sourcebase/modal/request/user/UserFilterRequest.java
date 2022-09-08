package com.lecuong.sourcebase.modal.request.user;

import com.lecuong.sourcebase.modal.request.BaseRequest;
import lombok.Data;

import java.util.List;

@Data
public class UserFilterRequest extends BaseRequest {

    private Long roleId;
    private Long blogId;
    private List<Long> userIds;
    private String userName;
    private String address;
    private String email;
}
