package com.lecuong.sourcebase.repository;

import com.lecuong.sourcebase.entity.Author;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends BaseRepository<Author, Long> {
}
