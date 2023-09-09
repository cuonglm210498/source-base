package com.lecuong.sourcebase.interceptor;

import com.lecuong.sourcebase.service.HttpLoggingService;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;


@ControllerAdvice
public class HttpRequestInterceptor extends RequestBodyAdviceAdapter {

    private final HttpServletRequest httpServletRequest;

    private final HttpLoggingService httpLoggingService;

    public HttpRequestInterceptor(HttpServletRequest httpServletRequest, HttpLoggingService httpLoggingService) {
        this.httpServletRequest = httpServletRequest;
        this.httpLoggingService = httpLoggingService;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        httpLoggingService.logRequest(httpServletRequest, body);

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

}
