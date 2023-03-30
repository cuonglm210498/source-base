package com.lecuong.sourcebase.controller.attachfile;

import com.lecuong.sourcebase.modal.request.attachfile.AttachFileRequest;
import com.lecuong.sourcebase.modal.response.AttachFileResponse;
import com.lecuong.sourcebase.service.AttachFileService;
import lombok.Data;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author CuongLM18
 * @created 30/03/2023 - 11:11 AM
 * @project source-base
 */
@Data
@RestController
@RequestMapping("/file")
public class AttachFileController {

    private final AttachFileService attachFileService;

    @PostMapping(value = "/upload")
    public ResponseEntity<Object> upload(@ModelAttribute AttachFileRequest request) {
        return ResponseEntity.ok().body(attachFileService.uploadFile(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        attachFileService.deleteFile(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> getFileById(@PathVariable Long id) {
        AttachFileResponse response = attachFileService.getFileById(id);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachfile;filename=" + response.getFileName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(response.getInputStream()));
    }
}
