package com.lecuong.sourcebase.controller.certificate;

import com.lecuong.sourcebase.modal.response.BaseResponse;
import com.lecuong.sourcebase.service.CertificateService;
import lombok.Data;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author cuong.lemanh10
 * @created 21/08/2023
 * @project demo
 */
@Data
@RestController
@RequestMapping("/certificate")
public class CertificateController {

    /**
     * Import file excel có check lỗi, nếu lỗi trả về file lỗi
     * Export file template
     */

    private final CertificateService certificateService;

    @PostMapping("/import-excel")
    public ResponseEntity<?> importCertificate(@RequestParam("file") MultipartFile file) throws IOException {

        Object object = certificateService.importExcel(file);

        if (object instanceof ByteArrayInputStream) {
            return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=users.xlsx")
                    .body(new InputStreamResource((InputStream) object));
        } else {
            return ResponseEntity.ok(BaseResponse.ofSuccess(object));
        }
    }

    @GetMapping("/get-template")
    public ResponseEntity<Resource> getTemplate() throws IOException {

        Resource resource = certificateService.getTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "ChungChi" + ".xlsx");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(resource);
    }
}
