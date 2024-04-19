package com.lecuong.sourcebase.service.impl;

import com.lecuong.sourcebase.constant.Constant;
import com.lecuong.sourcebase.entity.Certificate;
import com.lecuong.sourcebase.exception.BusinessException;
import com.lecuong.sourcebase.exception.StatusTemplate;
import com.lecuong.sourcebase.repository.CertificateRepository;
import com.lecuong.sourcebase.service.CertificateService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cuong.lemanh10
 * @created 21/08/2023
 * @project demo
 */
@Data
@Slf4j
@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository repository;

    @Override
    public Object importExcel(MultipartFile file) throws IOException {

        if (!file.getContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            log.error("File không đúng định dạng: " + file.getContentType());
            throw new BusinessException(StatusTemplate.FILE_INVALID);
        }

        var isSuccessAll = true;

        var workbook = new XSSFWorkbook(file.getInputStream());
        var sheet = workbook.getSheetAt(0);

        validateFileTemplate(sheet);

        Row headerRow = sheet.getRow(3);
        var errorHeaderCell = headerRow.createCell(Constant.CertificateConstant.RESULT_COLUMN);
        errorHeaderCell.setCellValue(Constant.CertificateConstant.RESULT_COLUMN_NAME);
        errorHeaderCell.setCellStyle(this.makeErrorHeaderCellStyle(workbook));
        CellStyle errorCellStyle = makeErrorCellStyle(workbook);
        List<Certificate> certificates = new ArrayList<>();

        int lastRowNum = sheet.getLastRowNum();

        for (int i = 5; i <= lastRowNum; i++) { // Bắt đầu từ dòng thứ 6 (bỏ qua header)
            Row dataRow = sheet.getRow(i);

            if (isRowEmpty(dataRow)) continue;

            Cell errorCell = dataRow.createCell(Constant.CertificateConstant.RESULT_COLUMN);
            errorCell.setCellStyle(errorCellStyle);

            Certificate certificate = new Certificate();

            // Cột nào đánh dấu là không được null thì dùng if
            if (readEmployeeId(dataRow, certificate, errorCell)) {
                isSuccessAll = false;
            }
            if (readType(dataRow, certificate, errorCell)) {
                isSuccessAll = false;
            }

            // Cột nào không đánh dấu là bắt buộc thì không cần dùng if như bên trên
            readField(dataRow, certificate, errorCell);

            if (readFullNameOfCertificate(dataRow, certificate, errorCell)) {
                isSuccessAll = false;
            }

            readAcronym(dataRow, certificate, errorCell);
            readIssuer(dataRow, certificate, errorCell);

            if (readDateRange(dataRow, certificate, errorCell)) {
                isSuccessAll = false;
            }

            readExpirationDate(dataRow, certificate, errorCell);

            certificates.add(certificate);
        }

        if (isSuccessAll) {
            repository.saveAll(certificates);
            return "success";
        } else {
            var byteArrayOutPutStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutPutStream);
            byte[] byteArray = byteArrayOutPutStream.toByteArray();
            return new ByteArrayInputStream(byteArrayOutPutStream.toByteArray());
        }
    }

    @Override
    public Resource getTemplate() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/templates/ChungChi.xlsx");
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(1);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
        return new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray()));
    }

    private void validateFileTemplate(Sheet sheet) {
        Row row = sheet.getRow(3); // Kiem tra header tai dong thu 3

        checkHeaderCellValue(row, Constant.CertificateConstant.STT_COLUMN, Constant.CertificateConstant.STT_COLUMN_NAME);
        checkHeaderCellValue(row, Constant.CertificateConstant.EMPLOYEE_COLUMN, Constant.CertificateConstant.EMPLOYEE_COLUMN_NAME);
        checkHeaderCellValue(row, Constant.CertificateConstant.TYPE_COLUMN, Constant.CertificateConstant.TYPE_COLUMN_NAME);
        checkHeaderCellValue(row, Constant.CertificateConstant.FIELD_COLUMN, Constant.CertificateConstant.FIELD_COLUMN_NAME);
        checkHeaderCellValue(row, Constant.CertificateConstant.NAME_CERTIFICATE_COLUMN, Constant.CertificateConstant.NAME_CERTIFICATE_COLUMN_NAME);
        checkHeaderCellValue(row, Constant.CertificateConstant.ACRONYM_COLUMN, Constant.CertificateConstant.ACRONYM_COLUMN_NAME);
        checkHeaderCellValue(row, Constant.CertificateConstant.ISSUER_COLUMN, Constant.CertificateConstant.ISSUER_COLUMN_NAME);
        checkHeaderCellValue(row, Constant.CertificateConstant.DATE_RANGE_COLUMN, Constant.CertificateConstant.DATE_RANGE_COLUMN_NAME);
        checkHeaderCellValue(row, Constant.CertificateConstant.EXPIRATION_DATE_COLUMN, Constant.CertificateConstant.EXPIRATION_DATE_COLUMN_NAME);
    }

    private void checkHeaderCellValue(Row row, int column, String headerText) {
        var sttCell = row.getCell(column);
        if (sttCell == null || !sttCell.getStringCellValue().trim().equalsIgnoreCase(headerText)) {
            throw new BusinessException(StatusTemplate.FILE_INVALID);
        }
    }

    private CellStyle makeErrorCellStyle(Workbook workbook) {
        var cellStyle = workbook.createCellStyle();
        var font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    private CellStyle makeErrorHeaderCellStyle(Workbook workbook) {
        var cellStyle = workbook.createCellStyle();
        var font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }

    private boolean isRowEmpty(Row row) {
        for (int c = 1; c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != CellType.BLANK)
                return false;
        }
        return true;
    }

    private boolean readEmployeeId(Row row, Certificate certificate, Cell errorCell) {
        var employeeIdCell = row.getCell(Constant.CertificateConstant.EMPLOYEE_COLUMN);
        if (!employeeIdCell.getStringCellValue().isBlank()) {
            String employeeId = employeeIdCell.getStringCellValue();
            certificate.setEmployeeId(Long.parseLong(employeeId));
            return false;
        } else {
            appendStringCellValue(errorCell, "Không được để trống");
            return true;
        }
    }

    private boolean readType(Row row, Certificate certificate, Cell errorCell) {
        var typeCell = row.getCell(Constant.CertificateConstant.TYPE_COLUMN);
        if (!typeCell.getStringCellValue().isBlank()) {
            String type = typeCell.getStringCellValue();
            certificate.setType(type);
            return false;
        } else {
            appendStringCellValue(errorCell, "Không được để trống");
            return true;
        }
    }

    private boolean readField(Row row, Certificate certificate, Cell errorCell) {
        var fieldCell = row.getCell(Constant.CertificateConstant.FIELD_COLUMN);
        String field = fieldCell.getStringCellValue();
        certificate.setField(field);
        return false;
    }

    private boolean readFullNameOfCertificate(Row row, Certificate certificate, Cell errorCell) {
        var nameCertificateCell = row.getCell(Constant.CertificateConstant.NAME_CERTIFICATE_COLUMN);
        if (!nameCertificateCell.getStringCellValue().isBlank()) {
            String nameCertificate = nameCertificateCell.getStringCellValue();
            certificate.setFullNameOfCertificate(nameCertificate);
            return false;
        } else {
            appendStringCellValue(errorCell, "Không được để trống");
            return true;
        }
    }

    private boolean readAcronym(Row row, Certificate certificate, Cell errorCell) {
        var acronymCell = row.getCell(Constant.CertificateConstant.ACRONYM_COLUMN);
        String acronym = acronymCell.getStringCellValue();
        certificate.setAcronym(acronym);
        return false;
    }

    private boolean readIssuer(Row row, Certificate certificate, Cell errorCell) {
        var issuerCell = row.getCell(Constant.CertificateConstant.ISSUER_COLUMN);
        String issuer = issuerCell.getStringCellValue();
        certificate.setIssuer(issuer);
        return false;
    }

    private boolean readDateRange(Row row, Certificate certificate, Cell errorCell) {
        var dateRangeCell = row.getCell(Constant.CertificateConstant.DATE_RANGE_COLUMN);
        if (!dateRangeCell.getStringCellValue().isBlank()) {
            try {
                String dateRange = dateRangeCell.getStringCellValue();
                certificate.setDateRange(new SimpleDateFormat("dd/MM/yyyy").parse(dateRange));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return false;
        } else {
            appendStringCellValue(errorCell, "Không được để trống");
            return true;
        }
    }

    private boolean readExpirationDate(Row row, Certificate certificate, Cell errorCell) {
        try {
            var expirationDateCell = row.getCell(Constant.CertificateConstant.EXPIRATION_DATE_COLUMN);
            String expirationDate = expirationDateCell.getStringCellValue();
            certificate.setExpirationDate(new SimpleDateFormat("dd/MM/yyyy").parse(expirationDate));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void appendStringCellValue(Cell cell, String newValue) {
        cell.setCellValue(cell.getStringCellValue() + "- " + newValue + "\n");
    }
}
