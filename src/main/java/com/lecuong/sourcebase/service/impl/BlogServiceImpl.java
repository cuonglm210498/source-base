package com.lecuong.sourcebase.service.impl;

import com.lecuong.sourcebase.entity.Blog;
import com.lecuong.sourcebase.mapper.BlogMapper;
import com.lecuong.sourcebase.modal.request.blog.BlogFilterRequest;
import com.lecuong.sourcebase.modal.response.BlogResponse;
import com.lecuong.sourcebase.repository.BlogRepository;
import com.lecuong.sourcebase.service.BlogService;
import com.lecuong.sourcebase.specification.BlogSpecification;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Data
@Service
public class BlogServiceImpl implements BlogService {

    private final BlogMapper blogMapper;
    private final BlogRepository blogRepository;

    @Override
    public Page<BlogResponse> getAll(BlogFilterRequest blogFilterRequest) {
        PageRequest pageRequest = PageRequest.of(blogFilterRequest.getPageIndex(), blogFilterRequest.getPageSize());
        Page<Blog> blogs = blogRepository.findAll(BlogSpecification.filter(blogFilterRequest), pageRequest.previousOrFirst());

        return blogs.map(blogMapper::to);
    }
}
