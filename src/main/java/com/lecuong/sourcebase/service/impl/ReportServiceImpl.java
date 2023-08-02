package com.lecuong.sourcebase.service.impl;

import com.lecuong.sourcebase.service.ReportService;
import lombok.SneakyThrows;
import org.jxls.common.Context;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * @author cuong.lemanh10
 * @created 31/07/2023
 * @project demo
 */
@Service
public class ReportServiceImpl implements ReportService {
    
    @Override
    @SneakyThrows
    public ByteArrayOutputStream genXlsxLocal(Map<String, Object> data, String templateRelativePathAndName) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(templateRelativePathAndName);
        if (in == null) {
            throw new RuntimeException("Template file not found:" + templateRelativePathAndName);
        }
        Context context = PoiTransformer.createInitialContext();
        for (Map.Entry<String, Object> d : data.entrySet()) {
            if (d.getKey() != null && d.getValue() != null) {
                context.putVar(d.getKey(), d.getValue());
            }
        }

        JxlsHelper.getInstance().processTemplate(in, out, context);
        return out;
    }

    @Override
    public InputStreamResource toInputStreamResource(ByteArrayOutputStream outputStream) {
        return new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray()));
    }

    @Override
    public InputStreamResource toInputStreamResource(ByteArrayInputStream outputStream) {
        return new InputStreamResource(outputStream);
    }
}
