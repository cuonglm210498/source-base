package com.lecuong.sourcebase.modal.request.user;

import com.lecuong.sourcebase.modal.request.BaseRequest;
import lombok.Data;

@Data
public class UserFilterRequest extends BaseRequest {

    private String userName;
    private String address;
    private String email;
}
