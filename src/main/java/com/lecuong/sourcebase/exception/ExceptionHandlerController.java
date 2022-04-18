package com.lecuong.sourcebase.exception;

import com.lecuong.sourcebase.modal.response.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@AllArgsConstructor
public class ExceptionHandlerController {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse<StatusResponse>> handlerBusinessException(BusinessException businessException) {
        return new ResponseEntity<>(BaseResponse.ofFail(businessException), businessException.getStatusResponse().getStatus());
    }
}
