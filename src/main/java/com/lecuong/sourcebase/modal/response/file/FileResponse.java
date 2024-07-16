package com.lecuong.sourcebase.modal.response.file;

import lombok.Data;

/**
 * @author CuongLM
 * @created 16/07/2024 - 21:24
 * @project source-base
 */
@Data
public class FileResponse {

    private long id;
    private String fileName;
    private Long size;
    private String type;
    private byte[] data;
}
