package com.lecuong.sourcebase.modal.response;

import lombok.Data;

@Data
public class BlogResponse {

    private long id;
    private String thumbnail;
    private String url;
    private String name;
    private String description;
    private String title;
    private String content;
    private String categoryName;
    private String author;
}
