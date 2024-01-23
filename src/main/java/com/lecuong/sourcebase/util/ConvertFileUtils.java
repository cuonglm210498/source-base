package com.lecuong.sourcebase.util;

import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfStamper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.*;
import java.nio.file.FileSystems;

/**
 * @author cuong.lemanh10
 * @created 29/11/2023
 * @project source-base-save-file-to-system
 */
public class ConvertFileUtils {

    private ConvertFileUtils() {}

    public static final String PLUTEXT_API = "http://localhost:9016/v1/00000000-0000-0000-0000-000000000000/convert";
    private static final String TEMP_FOLDER = "./temp/";
    private static final String PLUTEXT_PACKAGE = "com.plutext.converter.URL";
    private static final String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();

    public static InputStream convertDocxToPdf(InputStream inputStreamSource) throws IOException, Docx4JException, DocumentException {
        String tempFolderPath = (TEMP_FOLDER + String.valueOf(System.currentTimeMillis()) + PATH_SEPARATOR).replace("//", PATH_SEPARATOR);
        File folderTemp = new File(tempFolderPath);

        if (!folderTemp.exists()) {
            folderTemp.mkdir();
        }

        String timeMillisTemp = String.valueOf(System.currentTimeMillis());
        String fileTempPath = folderTemp.getAbsolutePath() + PATH_SEPARATOR + timeMillisTemp + ".docx";
        String fileResultTempPath = folderTemp.getAbsolutePath() + PATH_SEPARATOR + timeMillisTemp + ".pdf";
        File sourceFileTemp = new File(fileTempPath);
        File resultFileTemp = new File(fileResultTempPath);
        FileOutputStream fileOutputStream = new FileOutputStream(sourceFileTemp);

        try {
            byte[] bytes = org.apache.commons.compress.utils.IOUtils.toByteArray(inputStreamSource);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
        } finally {
            fileOutputStream.close();
        }

        FileOutputStream fileResultOutputStream = new FileOutputStream(resultFileTemp);

        try {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(sourceFileTemp);
            org.docx4j.Docx4jProperties.setProperty(PLUTEXT_PACKAGE, PLUTEXT_API);
            org.docx4j.Docx4J.toPDF(wordMLPackage, fileResultOutputStream);
            fileResultOutputStream.flush();
        } finally {
            fileResultOutputStream.close();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        com.itextpdf.text.pdf.PdfReader pdfReader = new com.itextpdf.text.pdf.PdfReader(new FileInputStream(resultFileTemp));
        PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);
        pdfStamper.close();

        try {
            sourceFileTemp.delete();
            resultFileTemp.delete();
            folderTemp.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    public static InputStream convertExcelToPdfDoc4j(InputStream excelFile) throws IOException {
        String tempFolderPath = (TEMP_FOLDER + String.valueOf(System.currentTimeMillis()) + PATH_SEPARATOR)
                .replace("//", PATH_SEPARATOR);

        File folderTemp = new File(tempFolderPath);
        if (!folderTemp.exists()) {
            folderTemp.mkdir();
        }

        String timeMillisTemp = String.valueOf(System.currentTimeMillis());
        String fileTempPath = folderTemp.getAbsolutePath() + PATH_SEPARATOR + timeMillisTemp + ".xlsx";

        FontProgramFactory.registerSystemFontDirectories();

        // Load Excel file
        Workbook workbook = new XSSFWorkbook(excelFile);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Create PDF writer and document
        com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter(byteArrayOutputStream);
        com.itextpdf.kernel.pdf.PdfDocument pdf = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdf, PageSize.A4.rotate());
        document.setMargins(20, 20, 20, 20);

        // Iterate over worksheets and create tables in PDF
        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            int lastRowNum = sheet.getLastRowNum();

            if (lastRowNum > 0) {
                Row firstRow = sheet.getRow(0);
                int numCols = firstRow.getPhysicalNumberOfCells();
                Table table = new Table(numCols).setWidth(UnitValue.createPercentValue(100));

                for (int i = 0; i <= lastRowNum; i++) {
                    Row row = sheet.getRow(i);

                    for (int j = 0; j < numCols; j++) {
                            Cell cell = row.getCell(j);

                        if (cell != null) {
                            com.itextpdf.layout.element.Cell pdfCell = new com.itextpdf.layout.element.Cell();
                            pdfCell.add(new com.itextpdf.layout.element.Paragraph(cell.toString()));
                            table.addCell(pdfCell);
                        } else {
                            table.addCell("");
                        }
                    }
                }

                document.add(table);
            }
        }

        // Close the PDF
        document.close();
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
}
