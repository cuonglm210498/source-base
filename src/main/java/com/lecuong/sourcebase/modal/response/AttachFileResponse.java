package com.lecuong.sourcebase.modal.response;

import lombok.Data;

import java.io.InputStream;

/**
 * @author CuongLM18
 * @created 30/03/2023 - 11:06 AM
 * @project source-base
 */
@Data
public class AttachFileResponse {

    private Long id;
    private String title;
    private String description;
    private String fileName;
    private String uri;
    private long size;
    private InputStream inputStream;
}
