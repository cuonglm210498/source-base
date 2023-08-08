package com.lecuong.sourcebase.modal.response.author;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lecuong.sourcebase.modal.response.book.BookResponse;
import lombok.Data;

import java.util.List;

@Data
public class AuthorResponse {

    private long id;
    private String name;
    private String address;
    @JsonProperty("bookResponses")
    private List<BookResponse> books;
}
