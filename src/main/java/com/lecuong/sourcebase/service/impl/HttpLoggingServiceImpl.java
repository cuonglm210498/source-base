package com.lecuong.sourcebase.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lecuong.sourcebase.service.HttpLoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
public class HttpLoggingServiceImpl implements HttpLoggingService {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpLoggingServiceImpl.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> parameters = buildParametersMap(httpServletRequest);

        stringBuilder.append("REQUEST ");
        stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
        stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");

        stringBuilder.append("headers=[").append(buildHeadersMap(httpServletRequest)).append("] ");
        if (!parameters.isEmpty()) {
            stringBuilder.append("parameters=[").append(parameters).append("] ");
        }

        if (body != null) {
            stringBuilder.append("body=[").append(writeBodyToString(body)).append("]");
        }

        String logMess = stringBuilder.toString();
        LOGGER.info(logMess);
    }

    @Override
    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {
        String response = "RESPONSE " +
                "method=[" + httpServletRequest.getMethod() + "] " +
                "path=[" + httpServletRequest.getRequestURI() + "] " +
                "responseHeaders=[" + buildHeadersMap(httpServletResponse) + "] " +
                "responseBody=[" + writeBodyToString(body) + "] ";
        LOGGER.info(response);
    }

    private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameters = httpServletRequest.getParameterNames();

        while (parameters.hasMoreElements()) {
            String key = parameters.nextElement();
            String value = httpServletRequest.getParameter(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }

    private Map<String, String> buildHeadersMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();

        Enumeration headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String key = (String) headers.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    private Map<String, String> buildHeadersMap(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();

        Collection<String> headers = response.getHeaderNames();
        headers.stream().forEach(header -> {
            map.put(header, response.getHeader(header));
        });

        return map;
    }

    private String writeBodyToString(Object body) {
        try {
            return objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            return "";
        }
    }

}
