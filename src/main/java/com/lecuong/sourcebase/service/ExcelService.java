package com.lecuong.sourcebase.service;

import com.lecuong.sourcebase.entity.ExcelEntity;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ExcelService {

    List<ExcelEntity> getDataFromFile(MultipartFile file);
    ResponseEntity<Resource> getTemplateTask() throws IOException;
    ResponseEntity<Resource> getTemplateScheduleTask() throws IOException;

}
