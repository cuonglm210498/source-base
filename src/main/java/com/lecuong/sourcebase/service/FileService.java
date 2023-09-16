package com.lecuong.sourcebase.service;

import com.lecuong.sourcebase.modal.request.file.FileRequest;
import com.lecuong.sourcebase.modal.response.file.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author CuongLM
 * @created 16/09/2023 - 10:29 PM
 * @project source-base
 */
public interface FileService {

     FileResponse encodeFileToBase64(MultipartFile file) throws IOException;

    void save(FileRequest request, byte[] data);

    FileResponse getFile(Long id);
}
