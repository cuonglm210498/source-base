package com.lecuong.sourcebase.mapper;

import com.lecuong.sourcebase.entity.Author;
import com.lecuong.sourcebase.modal.request.author.AuthorSaveRequest;
import com.lecuong.sourcebase.modal.response.author.AuthorResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthorMapper {

    private ModelMapper modelMapper;

    public Author to(AuthorSaveRequest request) {
        return modelMapper.map(request, Author.class);
    }

    public AuthorResponse to(Author author) {
        return modelMapper.map(author, AuthorResponse.class);
    }
}
