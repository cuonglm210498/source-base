package com.lecuong.sourcebase.mapper;

import com.lecuong.sourcebase.entity.File;
import com.lecuong.sourcebase.modal.response.file.FileResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author CuongLM
 * @created 16/07/2024 - 21:25
 * @project source-base
 */
@Component
@AllArgsConstructor
public class FileMapper {

    private ModelMapper mapper;

    public FileResponse to(File file) {
        return mapper.map(file, FileResponse.class);
    }
}
