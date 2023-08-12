package com.lecuong.sourcebase.util;

import com.lecuong.sourcebase.exception.BusinessException;
import com.lecuong.sourcebase.exception.StatusTemplate;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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
