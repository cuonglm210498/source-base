package com.lecuong.sourcebase.exceptionv2;

import com.lecuong.sourcebase.modal.response.BaseResponseV2;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author CuongLM
 * @created 25/09/2023 - 6:41 PM
 * @project source-base
 */
@RestControllerAdvice
@AllArgsConstructor
public class ExceptionHandlerControllerV2 {

    @ExceptionHandler(BusinessExceptionV2.class)
    public ResponseEntity<BaseResponseV2<ErrorCodeResponse>> handlerBusinessException(BusinessExceptionV2 e) {
        return new ResponseEntity<>(BaseResponseV2.ofFail(e), e.getErrorCodeResponse().getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponseV2<ErrorCodeResponse>> handlerAccessDeniedException(AccessDeniedException e) {
        return new ResponseEntity<>(BaseResponseV2.ofFail(e), HttpStatus.FORBIDDEN);
    }
}
