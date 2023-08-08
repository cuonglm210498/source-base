package com.lecuong.sourcebase.repository;

import com.lecuong.sourcebase.entity.Book;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends BaseRepository<Book, Long> {
}
