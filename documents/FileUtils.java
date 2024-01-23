package com.vsm.business.utils;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.vsm.business.common.AppConstant;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.FileSystems;

@Component
public class FileUtils {

    public final Logger log = LoggerFactory.getLogger(FileUtils.class);

    public File convertFile(File file){
        /*
            Chuyển đổi định dạng file sang pdf
        */
        return file;
    }

    @Value("${system.folder.TEMP_FOLDER:./temp/}")
    public String tempFolder;
    @Value("${system.folder.TEMP_FOLDER_WATERMARK:./temp/watermark/image_use_by_only.png}")
    public String IMAGE_USE_BY_ONLY_PNG;

    @Value("${system.plutext.config.host:http://localhost:9016/v1/00000000-0000-0000-0000-000000000000/convert}")
    public String PLUTEXT_API;
    private static final String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();

    public InputStream convertDocToPDFDOCX4J(InputStream inputStreamSource) throws IOException, Docx4JException, DocumentException {
        String tempFolderPath = (tempFolder + String.valueOf(System.currentTimeMillis()) + PATH_SEPARATOR).replace("//", PATH_SEPARATOR);
        File folderTemp = new File(tempFolderPath);
        if(!folderTemp.exists()) folderTemp.mkdir();
        String timeMillisTemp = String.valueOf(System.currentTimeMillis());
        String fileTempPath = folderTemp.getAbsolutePath() + PATH_SEPARATOR + "DuowngTora_" + timeMillisTemp + ".docx";
        String fileResultTempPath = folderTemp.getAbsolutePath() + PATH_SEPARATOR + "DuowngTora_" + timeMillisTemp + ".pdf";
        File sourceFileTemp = new File(fileTempPath);
        File resultFileTemp = new File(fileResultTempPath);

        FileOutputStream fileOutputStream = new FileOutputStream(sourceFileTemp);

        byte[] bytes = org.apache.commons.compress.utils.IOUtils.toByteArray(inputStreamSource);
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();


        FileOutputStream fileResultOutputStream = new FileOutputStream(resultFileTemp);
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(sourceFileTemp);
//        org.docx4j.Docx4jProperties.setProperty("com.plutext.converter.URL", "http://127.0.0.1:9016/v1/00000000-0000-0000-0000-000000000000/convert");
        org.docx4j.Docx4jProperties.setProperty("com.plutext.converter.URL", this.PLUTEXT_API);
        org.docx4j.Docx4J.toPDF(wordMLPackage,fileResultOutputStream);
        fileResultOutputStream.flush();
        fileResultOutputStream.close();


        Image qrCodeImage = Image.getInstance(IMAGE_USE_BY_ONLY_PNG.replaceAll("//", this.PATH_SEPARATOR));
        qrCodeImage.scaleAbsolute(16f, 160f);
        qrCodeImage.setAbsolutePosition(-6f, 64f);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfReader pdfReader = new PdfReader(new FileInputStream(resultFileTemp));
        PdfStamper pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream);
        PdfContentByte over;
        for(int i=1; i<=pdfReader.getNumberOfPages(); i++){
            PdfContentByte contentByte = pdfStamper.getUnderContent(i);
            contentByte.addImage(qrCodeImage);
            over = pdfStamper.getOverContent(i);
            over.saveState();
            PdfGState pdfGState = new PdfGState();
            pdfGState.setFillOpacity(1.0f);
            pdfGState.setOverPrintMode(1);
            pdfGState.setAlphaIsShape(true);
            over.setGState(pdfGState);
        }
        pdfStamper.close();
        try {
            sourceFileTemp.delete();
            resultFileTemp.delete();
            folderTemp.delete();
        }catch (Exception e){log.error("{}", e);}

        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    /**
     * Hàm xử lý đường đẫn file
     * @param fileName: đường dẫn
     * @return: đường dẫn mới
     */
    public String checkFileName(String fileName){
        if(fileName == null) return null;
        String newFilePath = fileName.replace(this.PATH_SEPARATOR, AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);
        newFilePath = newFilePath.replace("\\", "_");
        newFilePath = newFilePath.replaceAll("[\\/\\*<>?:|#%]", AppConstant.FileConstant.UNSAFE_SYMBOL_REPLACEMENT);
        return newFilePath;
    }
}
