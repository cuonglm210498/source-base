package com.lecuong.sourcebase.modal.request.user;

import com.lecuong.sourcebase.modal.request.BaseRequest;
import lombok.Data;

import java.util.List;

/**
 * @author CuongLM18
 * @created 09/08/2022 - 12:27 PM
 * @project source-base
 */
@Data
public class UserFilterWithListBlogRequest extends BaseRequest {

    private List<Long> ids;
}
