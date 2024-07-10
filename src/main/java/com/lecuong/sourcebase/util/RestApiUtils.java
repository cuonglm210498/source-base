package com.lecuong.sourcebase.util;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.lecuong.sourcebase.exception.BusinessException;
import com.lecuong.sourcebase.exception.StatusTemplate;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * @author CuongLM
 * @created 12/08/2023 - 1:28 PM
 * @project source-base
 */
@Component
@AllArgsConstructor
public class RestApiUtils {

    public static final String X_AUTH_TOKEN = "X-AUTH-TOKEN";
    public static final String BEARER = "Bearer ";

    private final RestTemplate restTemplate;

    /**
     * Function callRestApi được sử dụng để gọi đến API của hệ thống khác
     * @param path: url của API gọi đến
     * @param method: HttpMethod của API gọi đến
     * @param headers: header của API gọi đến
     * @param request: body của request
     * @param clazz: Kiểu trả về của class khi có response trả về
     */
    public <T> T callRestApi(final String path, final HttpMethod method, HttpHeaders headers, Object request, Class<T> clazz) {
        try {
            var response =
                    restTemplate.exchange(path, method, new HttpEntity<>(request, headers), clazz);

            if (HttpStatus.OK == response.getStatusCode() || HttpStatus.CREATED == response.getStatusCode())
                return response.getBody();
            return null;
        } catch (Exception e) {
            throw new BusinessException(StatusTemplate.REST_API_CALL);
        }
    }

    /**
     * call api with nobody in request
     */
    public <T> T callRestApi(final String path, final HttpMethod method, Class<T> clazz) {
        try {
            var response = restTemplate.exchange(
                    path,
                    method,
                    null,
                    clazz);

            if (HttpStatus.OK == response.getStatusCode() || HttpStatus.CREATED == response.getStatusCode())
                return response.getBody();
            return null;
        } catch (Exception e) {
            throw new BusinessException(StatusTemplate.REST_API_CALL);
        }
    }

    /**
     * call api with body in request
     */
    public <T> T callRestApi(final String path, final HttpMethod method, Object request, Class<T> clazz) {
        try {
            var response = restTemplate.exchange(
                    path,
                    method,
                    new HttpEntity<>(request),
                    clazz);

            if (HttpStatus.OK == response.getStatusCode() || HttpStatus.CREATED == response.getStatusCode())
                return response.getBody();
            return null;
        } catch (Exception e) {
            throw new BusinessException(StatusTemplate.REST_API_CALL);
        }
    }

    /**
     * call api with nobody and token in request
     */
    public <T> T callRestApiWithToken(final String path, final HttpMethod method, Object request, Class<T> clazz) {
        try {
            var response = restTemplate.exchange(
                    path,
                    method,
                    new HttpEntity<>(request, this.makeHeader()),
                    clazz);

            if (HttpStatus.OK == response.getStatusCode() || HttpStatus.CREATED == response.getStatusCode())
                return response.getBody();
            return null;
        } catch (Exception e) {
            throw new BusinessException(StatusTemplate.REST_API_CALL);
        }
    }

    public <T> List<T> callRestApiReturnList(final String path, final HttpMethod method, Class<T> clazz) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            var response = restTemplate.exchange(
                    path,
                    method,
                    new HttpEntity<>(null, this.makeHeader()),
                    new ParameterizedTypeReference<List<T>>() {
                        @Override
                        public Type getType() {
                            return TypeFactory.defaultInstance().constructCollectionType(List.class, clazz);
                        }
                    });
            if (HttpStatus.OK == response.getStatusCode() || HttpStatus.CREATED == response.getStatusCode())
                return response.getBody();
            return Collections.emptyList();
        } catch (Exception e) {
            throw new BusinessException(StatusTemplate.REST_API_CALL);
        }
    }

    public HttpHeaders makeHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        String token = ((HttpServletRequest) request).getHeader("X-AUTH-TOKEN");
        headers.add(X_AUTH_TOKEN, token);

        return headers;
    }
}
