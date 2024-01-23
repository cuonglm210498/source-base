package com.lecuong.sourcebase.controller.document;

import com.itextpdf.text.DocumentException;
import com.lecuong.sourcebase.controller.BaseController;
import com.lecuong.sourcebase.modal.response.BaseResponseV2;
import com.lecuong.sourcebase.service.DocumentService;
import com.lecuong.sourcebase.util.ConvertFileUtils;
import lombok.Data;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * @author CuongLM
 * @created 06/11/2023 - 7:33 PM
 * @project source-base
 */
@Data
@RestController
@RequestMapping("/document")
public class DocumentController extends BaseController {

    /**
     * Lưu file trên hệ thống
     * Get file
     * View file online
     * - Convert DOCX to PDF:
     * + Tạo folder temp ở thư mục root chứa project VD: project lưu ở thư mục D:\Project\java thì tạo folder temp ở ổ D:/
     * + Chạy lệnh docker pull plutext/plutext-document-services:latest
     * + Chạy lệnh docker run -d -p 9016:80 --name plutext plutext/plutext-document-services
     * + Code như trong API get-file-pdf
     * <p>
     * + Trong trường hợp server không thông mạng ra internet
     * + Export docker images ra file .tar: docker save --output myimage.tar myimage
     * + Load images từ file .tar: docker load -i myimage.tar
     * + Đổi tên images: docker tag image_id imagename:version
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

    @GetMapping("/get-file-pdf")
    public ResponseEntity<Resource> getFilePdf(@RequestParam String id) {
        try {
            Resource docxResource = documentService.getFile(id);
            String fileName = docxResource.getFilename();

            if (fileName.endsWith(".docx")) {
                InputStream inputStream = ConvertFileUtils.convertDocxToPdf(new FileInputStream(docxResource.getFile()));
                Resource resource = new ByteArrayResource(inputStream.readAllBytes());
                return viewFile(resource);
            } else if (fileName.endsWith(".pdf")) {
                return viewFile(docxResource);
            } else if (fileName.endsWith(".xlsx")) {
                InputStream inputStream = ConvertFileUtils.convertExcelToPdfDoc4j(new FileInputStream(docxResource.getFile()));
                Resource resource = new ByteArrayResource(inputStream.readAllBytes());
                return viewFile(resource);
            } else {
                return ResponseEntity.status(500).body(null);
            }
        } catch (IOException | Docx4JException | DocumentException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
