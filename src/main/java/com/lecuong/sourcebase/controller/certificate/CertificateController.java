package com.lecuong.sourcebase.controller.certificate;

import com.lecuong.sourcebase.entity.ExcelEntity;
import com.lecuong.sourcebase.modal.response.BaseResponse;
import com.lecuong.sourcebase.service.CertificateService;
import com.lecuong.sourcebase.service.ExcelService;
import lombok.Data;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
     * Import file excel để đọc dữ liệu từ file
     */

    private final CertificateService certificateService;
    private final ExcelService excelService;

    /**
     * Import file excel có check lỗi, nếu lỗi trả về file lỗi
     */
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

    /**
     * Import file excel để đọc dữ liệu từ file
     */
    @PostMapping(value = "/get-data-from-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<ExcelEntity> getDataFromFile(@RequestParam MultipartFile file) {
        List<ExcelEntity> excelEntities = excelService.getDataFromFile(file);
        return excelEntities;
    }

    /**
     * Export file template
     */
    @GetMapping("/get-template-task")
    public ResponseEntity<Resource> getTemplateTask() throws IOException {
        return excelService.getTemplateTask();
    }
}
