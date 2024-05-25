package com.lecuong.sourcebase.service.impl;

import com.lecuong.sourcebase.entity.ExcelEntity;
import com.lecuong.sourcebase.service.ExcelService;
import com.lecuong.sourcebase.util.DataUtils;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ExcelServiceImpl implements ExcelService {

    private Workbook getWorkbook(InputStream inputStream, String excelFilePath)
            throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    @SneakyThrows
    @Override
    public List<ExcelEntity> getDataFromFile(MultipartFile file) {

        Workbook workbook = getWorkbook(file.getInputStream(), file.getOriginalFilename());

        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();

        List<ExcelEntity> arr = new ArrayList<>();

        int rowNumber = 0;
        while (rows.hasNext()) {
            ExcelEntity rowData = new ExcelEntity();

            Set<String> errors = new HashSet<>();
            Row currentRow = rows.next();

            // skip header
            if (rowNumber < 1) {
                rowNumber++;
                continue;
            }

            Iterator<Cell> cellsInRow = currentRow.iterator();

            int cellIdx = 1;
            String cellStringValue = "";
            Integer cellIntValue = null;
            Double cellDoubleValue = null;
            Date cellDateValue = null;
            Object cellValue = null;
            while (cellsInRow.hasNext()) {
                Cell currentCell = cellsInRow.next();
                CellType cellType = currentCell.getCellType();

                if (cellType.equals(CellType.STRING)) {
                    cellValue = GetStringFromCell(currentCell, errors, "col" + cellIdx, false);
                } else if (cellType.equals(CellType.NUMERIC)) {
                    if (HSSFDateUtil.isCellDateFormatted(currentCell)) {
                        cellValue = GetDateFromCell(currentCell, errors, "col" + cellIdx, false);
                    } else {
                        cellValue = GetDoubleFromCell(currentCell, errors, "col" + cellIdx, false);
                    }
                } else {
                    cellValue = GetStringFromCell(currentCell, errors, "col" + cellIdx, false);
                }

                switch (cellIdx) {
                    case 1:
                        rowData.setCol1(cellValue);
                        break;
                    case 2:
                        rowData.setCol2(cellValue);
                        break;
                    case 3:
                        rowData.setCol3(cellValue);
                        break;
                    case 4:
                        rowData.setCol4(cellValue);
                        break;
                    case 5:
                        rowData.setCol5(cellValue);
                        break;
                    case 6:
                        rowData.setCol6(cellValue);
                        break;
                    case 7:
                        rowData.setCol7(cellValue);
                        break;
                    case 8:
                        rowData.setCol8(cellValue);
                        break;
                    case 9:
                        rowData.setCol9(cellValue);
                        break;
                    case 10:
                        rowData.setCol10(cellValue);
                        break;
                    default:
                        break;
                }

                cellIdx++;
            }
            if (DataUtils.isTrue(rowData.getCol1()))
                arr.add(rowData);
        }

        workbook.close();

        return arr;
    }

    @Override
    public ResponseEntity<Resource> getTemplateTask() throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/report/author_report_template.xlsx")) {
            Workbook workbook = new XSSFWorkbook(inputStream);
//            Sheet sheet = workbook.getSheetAt(1);

            String fileName = UUID.randomUUID().toString();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + ".xlsx");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
            InputStreamResource resFile = new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray()));
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(resFile);
        }
    }

    @Override
    public ResponseEntity<Resource> getTemplateScheduleTask() throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/import.templates/template_import_schedule_task.xlsx")) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=template_import_schedule_task.xlsx");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
            InputStreamResource resFile = new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray()));
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(resFile);
        }
    }

    private String GetStringFromCell(Cell currentCell, Set<String> errors, String columnName, boolean required) {
        try {
            if (required) {
                if (!DataUtils.isTrue(currentCell.getStringCellValue())) {
                    errors.add(columnName + " chưa được nhập");
                }
            }
            return currentCell.getStringCellValue();
        } catch (Exception ex) {
            errors.add(columnName + " không hợp lệ");
            return "";
        }
    }

    private Date GetDateFromCell(Cell currentCell, Set<String> errors, String columnName, boolean required) {
        try {
            if (required) {
                if (currentCell.getCellType().equals(CellType.BLANK)) {
                    errors.add(columnName + " chưa được nhập");
                }
            }
            if (currentCell.getCellType().equals(CellType.BLANK)) {
                return null;
            } else
                return currentCell.getDateCellValue();
        } catch (Exception ex) {
            errors.add(columnName + " không hợp lệ");
            return null;
        }
    }

    private Integer GetIntFromCell(Cell currentCell, Set<String> errors, String columnName, boolean required) {
        try {
            if (required) {
                if (currentCell.getCellType().equals(CellType.BLANK)) {
                    errors.add(columnName + " chưa được nhập");
                }
            }
            if (currentCell.getCellType().equals(CellType.BLANK)) {
                return null;
            } else
                return (int) currentCell.getNumericCellValue();
        } catch (Exception ex) {
            errors.add(columnName + " không hợp lệ");
            return null;
        }
    }

    private Double GetDoubleFromCell(Cell currentCell, Set<String> errors, String columnName, boolean required) {
        try {
            if (required) {
                if (currentCell.getCellType().equals(CellType.BLANK)) {
                    errors.add(columnName + " chưa được nhập");
                }
            }
            if (currentCell.getCellType().equals(CellType.BLANK)) {
                return null;
            } else
                return currentCell.getNumericCellValue();
        } catch (Exception ex) {
            errors.add(columnName + " không hợp lệ");
            return null;
        }
    }

}
