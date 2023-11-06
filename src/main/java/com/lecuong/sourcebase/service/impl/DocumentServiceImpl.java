package com.lecuong.sourcebase.service.impl;

import com.lecuong.sourcebase.entity.Document;
import com.lecuong.sourcebase.exceptionv2.BusinessCodeResponse;
import com.lecuong.sourcebase.exceptionv2.BusinessExceptionV2;
import com.lecuong.sourcebase.repository.DocumentRepository;
import com.lecuong.sourcebase.service.DocumentService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

/**
 * @author CuongLM
 * @created 06/11/2023 - 7:29 PM
 * @project source-base
 */
@Data
@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    @Value("${upload.path}")
    private String uploadPath;

    private final DocumentRepository documentRepository;

    @Override
    public Resource getFile(String id) throws IOException {
        Optional<Document> document = documentRepository.findByRefId(id);
        if (document.isPresent()) {
            Path rootLocation = Paths.get(this.uploadPath + "/" + id);
            Path file = rootLocation.resolve(document.get().getFileName());
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new BusinessExceptionV2(BusinessCodeResponse.DOCUMENT_FILE_ERROR);
            }
        } else {
            throw new BusinessExceptionV2(BusinessCodeResponse.DOCUMENT_NOT_FOUND);
        }
    }

    @Override
    public void upload(MultipartFile file) {
        Document document = new Document();
        String refId = UUID.randomUUID().toString();
        String fileName = file.getOriginalFilename();
        document.setRefId(refId);
        document.setFileName(fileName);
        document.setFilePath("/" + refId + "/" + fileName);
        document.setFileType(file.getContentType());
        document.setFileSize(file.getSize());

        documentRepository.save(document);

        Path rootLocation = Paths.get(this.uploadPath + "/" + refId);
        try {
            Path destinationFile = rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
                // This is a security check
            }
            File directory = new File(rootLocation.toString());
            if (!directory.exists()) {
                directory.mkdir();
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new BusinessExceptionV2(BusinessCodeResponse.DOCUMENT_FILE_ERROR);
        }
    }
}
