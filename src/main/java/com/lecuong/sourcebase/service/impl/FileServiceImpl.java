package com.lecuong.sourcebase.service.impl;

import com.lecuong.sourcebase.entity.File;
import com.lecuong.sourcebase.exception.BusinessException;
import com.lecuong.sourcebase.exception.StatusTemplate;
import com.lecuong.sourcebase.mapper.FileMapper;
import com.lecuong.sourcebase.modal.request.file.FileRequest;
import com.lecuong.sourcebase.modal.response.file.FileResponse;
import com.lecuong.sourcebase.repository.FileRepository;
import com.lecuong.sourcebase.service.FileService;
import com.lecuong.sourcebase.util.FileUtils;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * @author CuongLM
 * @created 16/09/2023 - 10:31 PM
 * @project source-base
 */
@Data
@Service
public class FileServiceImpl implements FileService {

    private final FileMapper fileMapper;
    private final FileRepository fileRepository;

    @Override
    public FileResponse encodeFileToBase64(MultipartFile file) throws IOException {
        FileResponse response = new FileResponse();
        response.setFileName(file.getOriginalFilename());
        response.setSize(file.getSize());
        response.setType(file.getContentType());
        response.setCode(FileUtils.convertFileToBase64(file));
        return response;
    }

    @Override
    public void save(FileRequest request, byte[] data) {
        Optional<File> fileOptional = fileRepository.findByFileName(request.getFileName());
        if (!fileOptional.isPresent()) {
            File file = new File();
            file.setFileName(request.getFileName());
            file.setSize(request.getSize());
            file.setType(request.getType());
            file.setData(data);
            fileRepository.save(file);
        } else {
            throw new BusinessException(StatusTemplate.FILE_EXISTS);
        }
    }

    @Override
    public FileResponse getFile(Long id) {
        Optional<File> file = fileRepository.findById(id);
        if (file.isPresent()) {
            return fileMapper.to(file.get());
        } else {
            throw new BusinessException(StatusTemplate.FILE_NOT_FOUND);
        }
    }
}
