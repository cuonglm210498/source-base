package com.lecuong.sourcebase.service;

import com.lecuong.sourcebase.modal.request.author.AuthorSaveRequest;
import com.lecuong.sourcebase.modal.request.author.AuthorUpdateRequest;
import com.lecuong.sourcebase.modal.response.author.AuthorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorService {

    void save(AuthorSaveRequest request);

    Page<AuthorResponse> getAllAuthor(Pageable pageable);

    AuthorResponse findById(Long id);

    void update(Long id, AuthorUpdateRequest request);

    List<AuthorResponse> getAll();
}
