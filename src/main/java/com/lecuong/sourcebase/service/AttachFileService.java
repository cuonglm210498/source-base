package com.lecuong.sourcebase.service;

import com.lecuong.sourcebase.modal.request.attachfile.AttachFileRequest;
import com.lecuong.sourcebase.modal.response.attachfile.AttachFileResponse;

import java.util.List;

/**
 * @author CuongLM18
 * @created 30/03/2023 - 11:04 AM
 * @project source-base
 */
public interface AttachFileService {

    List<AttachFileResponse> uploadFile(AttachFileRequest request);

    void deleteFile(Long id);

    List<AttachFileResponse> getAllFile();

    AttachFileResponse getFileById(Long id);
}
