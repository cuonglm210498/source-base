package com.lecuong.sourcebase.controller.file;

import com.lecuong.sourcebase.modal.response.BaseResponse;
import com.lecuong.sourcebase.modal.response.file.FileResponse;
import com.lecuong.sourcebase.service.FileService;
import lombok.Data;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author CuongLM
 * @created 16/07/2024 - 21:44
 * @project source-base
 */
@Data
@RestController
@RequestMapping("/save-file-to-database")
public class FileController {

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<BaseResponse<FileResponse>> save(@RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.ok(BaseResponse.ofSuccess(fileService.save(file)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ByteArrayResource> getFile(@PathVariable Long id) throws IOException {
        FileResponse fileResponse = fileService.getFile(id);

        ByteArrayResource resource = new ByteArrayResource(fileResponse.getData());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileResponse.getFileName());
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileResponse.getData().length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
