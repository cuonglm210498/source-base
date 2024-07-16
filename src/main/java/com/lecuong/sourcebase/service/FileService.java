package com.lecuong.sourcebase.service;

import com.lecuong.sourcebase.modal.response.file.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author CuongLM
 * @created 16/07/2024 - 21:26
 * @project source-base
 */
public interface FileService {

    FileResponse save(MultipartFile file) throws IOException;

    FileResponse getFile(Long id);
}
