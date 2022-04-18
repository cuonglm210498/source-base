package com.lecuong.sourcebase.modal.request.blog;

import com.lecuong.sourcebase.modal.request.BaseRequest;
import lombok.Data;

@Data
public class BlogFilterRequest extends BaseRequest {

    private String name;
    private String url;
    private Long categoryId;
    private Long userId;
    private String keyword;
}
