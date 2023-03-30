package com.lecuong.sourcebase.modal.request.attachfile;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author CuongLM18
 * @created 30/03/2023 - 11:06 AM
 * @project source-base
 */
@Data
public class AttachFileRequest {

    private String title;

    private String description;

    @SuppressWarnings("java:S1948")
    private MultipartFile[] file;
}
