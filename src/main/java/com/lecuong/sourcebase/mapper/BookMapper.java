package com.lecuong.sourcebase.mapper;

import com.lecuong.sourcebase.entity.Book;
import com.lecuong.sourcebase.modal.request.author.BookSaveRequest;
import com.lecuong.sourcebase.modal.response.book.BookResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookMapper {

    private ModelMapper modelMapper;

    public Book to(BookSaveRequest request) {
        return modelMapper.map(request, Book.class);
    }

    public BookResponse to(Book book) {
        return modelMapper.map(book, BookResponse.class);
    }
}
