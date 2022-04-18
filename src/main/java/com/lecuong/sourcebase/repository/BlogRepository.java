package com.lecuong.sourcebase.repository;

import com.lecuong.sourcebase.entity.Blog;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends BaseRepository<Blog, Long> {
}
