package com.lecuong.sourcebase.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @author CuongLM
 * @created 06/11/2023 - 7:27 PM
 * @project source-base
 */
public interface DocumentService {

    Resource getFile(String id) throws IOException;

    void upload(MultipartFile file);
}
