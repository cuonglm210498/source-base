package com.lecuong.sourcebase.modal.response.file;

import lombok.Data;

/**
 * @author CuongLM
 * @created 16/09/2023 - 10:29 PM
 * @project source-base
 */
@Data
public class FileResponse {

    private long id;
    private String fileName;
    private Long size;
    private String type;
    private byte[] data;
    private String code;
}
