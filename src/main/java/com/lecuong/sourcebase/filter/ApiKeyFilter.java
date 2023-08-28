package com.lecuong.sourcebase.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lecuong.sourcebase.constant.Constant;
import com.lecuong.sourcebase.exception.BusinessException;
import com.lecuong.sourcebase.exception.StatusTemplate;
import com.lecuong.sourcebase.modal.response.BaseResponse;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author CuongLM
 * @created 28/08/2023 - 10:00 PM
 * @project source-base
 */
public class ApiKeyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        String apiKey = servletRequest.getHeader(Constant.ApiKey.API_KEY_HEADER);

        BusinessException error;
        ObjectMapper mapper = new ObjectMapper();

        if (apiKey != null && apiKey.equals(Constant.ApiKey.VALID_API_KEY)) {
            filterChain.doFilter(request, response);
        } else {
            error = new BusinessException(StatusTemplate.INVALID_API_KEY);
            mapper.writeValue(servletResponse.getOutputStream(), BaseResponse.ofFail(error));
        }
    }
}

