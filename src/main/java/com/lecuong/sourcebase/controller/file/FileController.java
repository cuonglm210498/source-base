package com.lecuong.sourcebase.controller.file;

import com.lecuong.sourcebase.exception.BusinessException;
import com.lecuong.sourcebase.exception.StatusTemplate;
import com.lecuong.sourcebase.modal.request.file.FileRequest;
import com.lecuong.sourcebase.modal.response.BaseResponse;
import com.lecuong.sourcebase.modal.response.file.FileResponse;
import com.lecuong.sourcebase.service.FileService;
import com.lecuong.sourcebase.util.FileUtils;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author CuongLM
 * @created 16/09/2023 - 10:19 PM
 * @project source-base
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("send-file")
    public ResponseEntity<BaseResponse<FileResponse>> encodeFile(@RequestParam MultipartFile file) {
        try {
            return ResponseEntity.ok(BaseResponse.ofSuccess(fileService.encodeFileToBase64(file)));
        } catch (IOException e) {
            throw new BusinessException(StatusTemplate.FILE_INVALID);
        }
    }

    @PostMapping("receive-file")
    public ResponseEntity<ByteArrayResource> decodeAndSaveFile(@RequestBody FileRequest request) {
        byte[] fileBytes = FileUtils.convertBase64ToByteArray(request.getCode());
        ByteArrayResource resource = new ByteArrayResource(fileBytes);

        fileService.save(request, fileBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + request.getFileName());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileBytes.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ByteArrayResource> getFile(@PathVariable Long id) {
        FileResponse fileResponse = fileService.getFile(id);

        ByteArrayResource resource = new ByteArrayResource(fileResponse.getData());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileResponse.getFileName());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileResponse.getData().length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
