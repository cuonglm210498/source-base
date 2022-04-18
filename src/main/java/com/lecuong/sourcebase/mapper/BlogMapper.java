package com.lecuong.sourcebase.mapper;

import com.lecuong.sourcebase.entity.Blog;
import com.lecuong.sourcebase.modal.request.blog.BlogSaveRequest;
import com.lecuong.sourcebase.modal.response.BlogResponse;
import com.lecuong.sourcebase.repository.CategoryRespository;
import com.lecuong.sourcebase.repository.UserRepository;
import com.lecuong.sourcebase.util.BeanUtils;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class BlogMapper {

    private final CategoryRespository categoryRespository;
    private final UserRepository userRepository;

    public BlogResponse to(Blog blog) {
        BlogResponse blogResponse = new BlogResponse();
        BeanUtils.refine(blog, blogResponse, BeanUtils::copyNonNull);

        if (blog.getCategory() != null) {
            blogResponse.setCategoryName(blog.getCategory().getName());
        }

        if (blog.getUser() != null) {
            blogResponse.setAuthor(blog.getUser().getUserName());
        }

        return blogResponse;
    }

    public Blog to(BlogSaveRequest blogSaveRequest) {
        Blog blog = new Blog();
        BeanUtils.refine(blogSaveRequest, blog, BeanUtils::copyNonNull);

        blog.setCategory(categoryRespository.findById(blogSaveRequest.getCategoryId()).get());
        blog.setUser(userRepository.findById(blogSaveRequest.getUserId()).get());

        return blog;
    }
}
