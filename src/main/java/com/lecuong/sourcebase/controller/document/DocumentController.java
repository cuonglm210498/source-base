package com.lecuong.sourcebase.controller.document;

import com.lecuong.sourcebase.modal.response.BaseResponseV2;
import com.lecuong.sourcebase.service.DocumentService;
import lombok.Data;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author CuongLM
 * @created 06/11/2023 - 7:33 PM
 * @project source-base
 */
@Data
@RestController
@RequestMapping("/document")
public class DocumentController {

    /**
     * Lưu file trên hệ thống
     * Get file
     * View file online
     */

    private final DocumentService documentService;

    @PostMapping(value = "upload")
    public ResponseEntity<BaseResponseV2<Void>> upload(@RequestParam MultipartFile file) {
        documentService.upload(file);
        return ResponseEntity.ok(BaseResponseV2.ofSuccess(null));
    }

    @GetMapping("get-file")
    public ResponseEntity<Resource> getFile(@RequestParam String id) throws IOException {
        Resource resource = documentService.getFile(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFile().getName());
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.getFile().length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
