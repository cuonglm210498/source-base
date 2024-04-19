package com.lecuong.sourcebase.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author cuong.lemanh10
 * @created 21/08/2023
 * @project demo
 */
public interface CertificateService {

    Object importExcel(MultipartFile file) throws IOException;

    Resource getTemplate() throws IOException;
}
