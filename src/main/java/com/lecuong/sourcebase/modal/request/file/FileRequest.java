package com.lecuong.sourcebase.modal.request.file;

import lombok.Data;

/**
 * @author CuongLM
 * @created 16/09/2023 - 10:28 PM
 * @project source-base
 */
@Data
public class FileRequest {

    private String fileName;
    private Long size;
    private String type;
    private String code;
}
