package com.lecuong.sourcebase.mapper;

import com.lecuong.sourcebase.entity.File;
import com.lecuong.sourcebase.modal.request.file.FileRequest;
import com.lecuong.sourcebase.modal.response.file.FileResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author CuongLM
 * @created 17/09/2023 - 12:32 AM
 * @project source-base
 */
@Component
@AllArgsConstructor
public class FileMapper {

    private ModelMapper mapper;

    public File to(FileRequest request) {
        return mapper.map(request, File.class);
    }

    public FileResponse to(File file) {
        return mapper.map(file, FileResponse.class);
    }
}
