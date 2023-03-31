package com.lecuong.sourcebase.service.impl;

import com.lecuong.sourcebase.entity.AttachFile;
import com.lecuong.sourcebase.modal.request.attachfile.AttachFileRequest;
import com.lecuong.sourcebase.modal.response.AttachFileResponse;
import com.lecuong.sourcebase.repository.AttachFileRepository;
import com.lecuong.sourcebase.service.AttachFileService;
import io.minio.*;
import io.minio.messages.Item;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CuongLM18
 * @created 30/03/2023 - 11:07 AM
 * @project source-base
 */
@Slf4j
@Data
@Service
@PropertySources({@PropertySource(value = "classpath:minio.properties")})
public class AttachFileServiceImpl implements AttachFileService {

    @Value("${minio.bucketName}")
    private String bucketName;

    private final MinioClient minioClient;

    private final AttachFileRepository attachFileRepository;

    private static final String FOLDER_NAME = "attachments/";
    private static final String[] ACCEPT_CONTENT_TYPE = {
            "application/zip", "application/vnd.rar",
            "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/pdf", "video/mp4", "audio/mpeg", "image/jpeg", "image/png", "image/gif",
            "image/bmp", "application/x-zip-compressed",
            "application/vnd.ms-excel.sheet.binary.macroenabled.12",
    };

    @Override
    public List<AttachFileResponse> uploadFile(AttachFileRequest request) {
        MultipartFile[] files = request.getFile();
        List<AttachFile> attachFiles = new ArrayList<>();

        if (files.length == 0) {
            return null;
        }

        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        for (MultipartFile file : files) {
            if (file != null && file.getOriginalFilename() != null) {
                this.checkContentType(file);
                int lastPointIndex = file.getOriginalFilename().lastIndexOf(".");
                String objectName = FOLDER_NAME + file.getOriginalFilename().substring(0, lastPointIndex) + "-" + Instant.now().toEpochMilli()
                        + file.getOriginalFilename().substring(lastPointIndex);

                try {
                    minioClient.putObject(PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
                } catch (Exception e) {
                    throw new RuntimeException("Thao tác với file thất bại");
                }

                //save to db
                AttachFile attachFile = new AttachFile();
                attachFile.setFileName(file.getOriginalFilename());
                attachFile.setTitle(request.getTitle());
                attachFile.setDescription(request.getDescription());
                attachFile.setUri(objectName);
                attachFile.setSize(file.getSize());
                attachFiles.add(attachFile);
            }
        }

        List<AttachFile> attachFileList = attachFileRepository.saveAll(attachFiles);

        return attachFileList
                .stream()
                .map(attachFile -> {
                    AttachFileResponse response = new AttachFileResponse();
                    response.setId(attachFile.getId());
                    response.setFileName(attachFile.getFileName());
                    response.setTitle(attachFile.getTitle());
                    response.setDescription(attachFile.getDescription());
                    response.setUri(attachFile.getUri());
                    response.setSize(attachFile.getSize());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFile(Long id) {
        AttachFile attachFile = attachFileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tồn tại file"));

        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(attachFile.getUri())
                    .build());

            attachFileRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Thao tác với file thất bại");
        }

    }

    @Override
    public List<AttachFileResponse> getAllFile() {
        List<AttachFileResponse> files = new ArrayList<>();
        try {
            Iterable<Result<Item>> result = minioClient.listObjects(ListObjectsArgs.builder()
                    .bucket(bucketName)
                    .recursive(true)
                    .build());
            for (Result<Item> item : result) {
                AttachFileResponse response = new AttachFileResponse();
                response.setFileName(item.get().objectName());
                response.setSize(item.get().size());
                response.setUri(item.get().objectName());
                files.add(response);
            }
            return files;
        } catch (Exception e) {
            log.error("Lỗi thao tác với file");
        }
        return files;
    }

    @Override
    public AttachFileResponse getFileById(Long id) {
        AttachFile attachFile = attachFileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tồn tại file"));

        try {
            GetObjectResponse fileResponse = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(attachFile.getUri())
                    .build());

            AttachFileResponse response = new AttachFileResponse();
            response.setFileName(attachFile.getFileName());
            response.setTitle(attachFile.getTitle());
            response.setId(attachFile.getId());
            response.setDescription(attachFile.getDescription());
            response.setSize(attachFile.getSize());
            response.setInputStream(new ByteArrayInputStream(fileResponse.readAllBytes()));

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi thao tác với file");
        }
    }

    private void checkContentType(MultipartFile file) {
        String contentType = file.getContentType();
        Boolean isNotInAcceptContentType = Arrays.stream(ACCEPT_CONTENT_TYPE).noneMatch(s -> s.equalsIgnoreCase(contentType));

        if (contentType == null || isNotInAcceptContentType) {
            throw new RuntimeException("Định dạng file không hợp lệ. Bạn cần chọn loại file khác! Định dạng file thỏa mãn bao gồm: excel, pdf, word, mp3, mp4, rar, zip, file ảnh");
        }
    }

}
