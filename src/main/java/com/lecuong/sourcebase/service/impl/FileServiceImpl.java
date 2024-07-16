package com.lecuong.sourcebase.service.impl;

import com.lecuong.sourcebase.entity.File;
import com.lecuong.sourcebase.exception.BusinessException;
import com.lecuong.sourcebase.exception.StatusTemplate;
import com.lecuong.sourcebase.mapper.FileMapper;
import com.lecuong.sourcebase.modal.response.file.FileResponse;
import com.lecuong.sourcebase.repository.FileRepository;
import com.lecuong.sourcebase.service.FileService;
import com.lecuong.sourcebase.util.AlgorithmUtils;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * @author CuongLM
 * @created 16/07/2024 - 21:27
 * @project source-base
 */
@Data
@Service
public class FileServiceImpl implements FileService {

    private final FileMapper fileMapper;
    private final FileRepository fileRepository;

    @Override
    public FileResponse save(MultipartFile file) throws IOException {
        File fileSave = new File();
        fileSave.setFileName(file.getOriginalFilename());
        fileSave.setSize(file.getSize());
        fileSave.setType(file.getContentType());
        fileSave.setData(AlgorithmUtils.convertMultipartFileToByteArray(file));

        fileRepository.save(fileSave);
        return fileMapper.to(fileSave);
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
