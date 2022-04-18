package com.lecuong.sourcebase.service;

import com.lecuong.sourcebase.modal.request.blog.BlogFilterRequest;
import com.lecuong.sourcebase.modal.response.BlogResponse;
import org.springframework.data.domain.Page;

public interface BlogService {

    Page<BlogResponse> getAll(BlogFilterRequest blogFilterRequest);
}
