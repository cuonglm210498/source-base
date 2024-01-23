package com.lecuong.sourcebase.controller;

import com.lecuong.sourcebase.modal.response.BaseResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

/**
 * @author cuong.lemanh10
 * @created 02/08/2023
 * @project source-base
 */
public class BaseController {

    public <T> ResponseEntity<T> success(T data) {
        return ResponseEntity.ok(data);
    }

    public ResponseEntity<InputStreamResource> downloadFile(String fileName, InputStreamResource input) {
        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                // Content-Type
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(input);
    }

    public ResponseEntity<Resource> viewFile(Resource resource) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.pdf");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
