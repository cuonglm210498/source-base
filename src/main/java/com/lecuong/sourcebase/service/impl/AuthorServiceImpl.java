package com.lecuong.sourcebase.service.impl;


import com.lecuong.sourcebase.entity.Author;
import com.lecuong.sourcebase.entity.Book;
import com.lecuong.sourcebase.mapper.AuthorMapper;
import com.lecuong.sourcebase.mapper.BookMapper;
import com.lecuong.sourcebase.modal.request.author.AuthorSaveRequest;
import com.lecuong.sourcebase.modal.request.author.AuthorUpdateRequest;
import com.lecuong.sourcebase.modal.response.author.AuthorResponse;
import com.lecuong.sourcebase.modal.response.book.BookResponse;
import com.lecuong.sourcebase.repository.AuthorRepository;
import com.lecuong.sourcebase.repository.BookRepository;
import com.lecuong.sourcebase.service.AuthorService;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
public class AuthorServiceImpl implements AuthorService {

    /**
     * Để map dữ liệu từ Request đến Entity hoặc từ Entity về Response
     * Cần phải kiểm tra cùng tên và cùng kiểu dữ liệu trả về thì mới map được
     * VD: ở AuthorResponse có List<BookResponse> thì ta phải đặt tên là List<BookResponse> books thì mới map được
     * Tương tự ở Request cũng thế
     */

    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Override
    public void save(AuthorSaveRequest request) {
        Author author = authorMapper.to(request);
        List<Book> books = request.getBookSaveRequests().stream().map(bookMapper::to).collect(Collectors.toList());
        author.setBooks(books);
        books.forEach(book -> book.setAuthor(author));
        authorRepository.save(author);
    }

    @Override
    public Page<AuthorResponse> getAllAuthor(Pageable pageable) {
        Page<Author> authors = authorRepository.findAll(pageable.previousOrFirst());
//        Page<AuthorResponse> authorResponses = authors.map(authorMapper::to);
//
//        authorResponses.forEach(authorResponse -> {
//            authors.forEach(author -> {
//                if (authorResponse.getId() == author.getId()) {
//                    List<BookResponse> responses = author.getBooks().stream().map(bookMapper::to).collect(Collectors.toList());
//                    authorResponse.setBookResponses(responses);
//                }
//            });
//        });

        return authors.map(authorMapper::to);
    }

    @Override
    public AuthorResponse findById(Long id) {
        Optional<Author> author = authorRepository.findById(id);

        if (author.isPresent()) {
            List<BookResponse> bookResponses = author.get().getBooks().stream().map(bookMapper::to).collect(Collectors.toList());
            AuthorResponse authorResponse = authorMapper.to(author.get());
            authorResponse.setBooks(bookResponses);
            return authorResponse;
        } else {
            throw new RuntimeException("Không tìm thấy author!!!");
        }
    }

    @Override
    public void update(Long id, AuthorUpdateRequest request) {
        Optional<Author> author = authorRepository.findById(id);

        if (author.isPresent()) {
            author.get().setName(request.getName());
            author.get().setAddress(request.getAddress());
            authorRepository.save(author.get());
        } else {
            throw new RuntimeException("Author not found!!!");
        }
    }

    @Override
    public List<AuthorResponse> getAll() {
        return authorRepository.findAll().stream().map(authorMapper::to).collect(Collectors.toList());
    }
}
