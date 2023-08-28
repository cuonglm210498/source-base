package com.lecuong.sourcebase.controller.certificate;

import com.lecuong.sourcebase.modal.response.BaseResponse;
import com.lecuong.sourcebase.service.CertificateService;
import lombok.Data;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
