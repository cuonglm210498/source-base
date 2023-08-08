package com.lecuong.sourcebase.modal.request.author;

import lombok.Data;

import java.util.List;

@Data
public class AuthorSaveRequest {

    private String name;
    private String address;
    private List<BookSaveRequest> bookSaveRequests;
}
