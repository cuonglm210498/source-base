package com.lecuong.sourcebase.service;

import org.springframework.core.io.InputStreamResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * @author cuong.lemanh10
 * @created 31/07/2023
 * @project demo
 */
public interface ReportService {

    ByteArrayOutputStream genXlsxLocal(Map<String, Object> data, String templateRelativePathAndName);

    InputStreamResource toInputStreamResource(ByteArrayOutputStream outputStream);

    InputStreamResource toInputStreamResource(ByteArrayInputStream outputStream);
}
