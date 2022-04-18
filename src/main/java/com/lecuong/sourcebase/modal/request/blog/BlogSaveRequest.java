package com.lecuong.sourcebase.modal.request.blog;

import lombok.Data;

@Data
public class BlogSaveRequest {

    private String thumbnail;
    private String url;
    private String name;
    private String description;
    private String title;
    private String content;
    private Long userId;
    private Long categoryId;
}
