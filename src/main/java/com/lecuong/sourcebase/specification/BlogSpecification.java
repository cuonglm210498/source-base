package com.lecuong.sourcebase.specification;

import com.lecuong.sourcebase.entity.Blog;
import com.lecuong.sourcebase.modal.request.blog.BlogFilterRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class BlogSpecification {

    public static Specification<Blog> filter(BlogFilterRequest blogFilterRequest) {
        return Specification.where(withName(blogFilterRequest.getName()))
                .and(withUrl(blogFilterRequest.getUrl()))
                .and(withCategoryId(blogFilterRequest.getCategoryId()))
                .and(withUserId(blogFilterRequest.getUserId()))
                .and(withKeyWord(blogFilterRequest.getKeyword()));
    }

    public static Specification<Blog> withName(String name) {
        if (StringUtils.isBlank(name))
            return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }

    public static Specification<Blog> withUrl(String url) {
        if (StringUtils.isBlank(url))
            return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("url"), url);
    }

    public static Specification<Blog> withCategoryId(Long categoryId) {
        if (categoryId == 0 || categoryId == null)
            return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join("category").get("id"), categoryId);
    }

    public static Specification<Blog> withUserId(Long userId) {
        if (userId == 0 || userId == null)
            return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join("user").get("id"), userId);
    }

    public static Specification<Blog> withKeyWord(String keyWord) {
        if (StringUtils.isBlank(keyWord))
            return null;
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("content"), "%" + keyWord + "%");
    }
}
