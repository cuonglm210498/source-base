package com.lecuong.sourcebase.controller.author;

import com.lecuong.sourcebase.common.DateTimeCommon;
import com.lecuong.sourcebase.constant.TemplateReportConstant;
import com.lecuong.sourcebase.controller.BaseController;
import com.lecuong.sourcebase.modal.request.author.AuthorSaveRequest;
import com.lecuong.sourcebase.modal.response.author.AuthorResponse;
import com.lecuong.sourcebase.repository.AuthorRepository;
import com.lecuong.sourcebase.service.AuthorService;
import com.lecuong.sourcebase.service.ReportService;
import lombok.Data;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Data
@RestController
@RequestMapping("/author")
public class AuthorController extends BaseController {

    /**
     * Export file excel
     */

    private final ReportService reportService;
    private final AuthorRepository repository;
    private final AuthorService authorService;
    private final String FILE_NAME = "author_report_%s.xlsx";
    private final String FILE_NAME_TEMPLATE = "author_report.xlsx";

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody AuthorSaveRequest request) {
        /**
         * {
         *     "name":"a",
         *     "address":"HN",
         *     "books": [
         *         {
         *             "name":"book1",
         *             "content":"a"
         *         },
         *         {
         *             "name":"book2",
         *             "content":"b"
         *         }
         *     ]
         * }
         */
        authorService.save(request);
        return success(null);
    }

    @GetMapping
    public ResponseEntity<Page<AuthorResponse>> getAll(@RequestParam int index,
                                                       @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(index, size);
        return ResponseEntity.ok(authorService.getAllAuthor(pageRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.findById(id));
    }

    @GetMapping("/export-excel")
    public ResponseEntity<InputStreamResource> export() {
        String fileName = String.format(FILE_NAME, new SimpleDateFormat(DateTimeCommon.DateTimeFormat.DD_MM_YY).format(new Date()));

        var data = new HashMap<String, Object>();
        List<AuthorResponse> responses = authorService.getAll();
        data.put("data", responses);

        return downloadFile(
                fileName,
                reportService.toInputStreamResource(reportService.genXlsxLocal(data, TemplateReportConstant.AUTHOR_REPORT_TEMPLATE))
        );
    }

    @GetMapping("/export-template")
    public ResponseEntity<InputStreamResource> exportTemplate() {
        var data = new HashMap<String, Object>();
        data.put("data", new ArrayList<>());

        return downloadFile(
                FILE_NAME_TEMPLATE,
                reportService.toInputStreamResource(
                        reportService.genXlsxLocal(
                                data,
                                TemplateReportConstant.AUTHOR_IMPORT_TEMPLATE))
        );
    }
}
