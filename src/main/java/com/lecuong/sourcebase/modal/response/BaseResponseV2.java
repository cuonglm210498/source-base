package com.lecuong.sourcebase.modal.response;

import com.lecuong.sourcebase.exceptionv2.BusinessCodeResponse;
import com.lecuong.sourcebase.exceptionv2.BusinessExceptionV2;
import com.lecuong.sourcebase.exceptionv2.ErrorCodeResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CuongLM
 * @created 25/09/2023 - 6:42 PM
 * @project source-base
 */
@Getter
@Setter
public class BaseResponseV2<T> {

    private ErrorCodeResponse code;
    private T data;

    private BaseResponseV2(ErrorCodeResponse code) {
        this.code = code;
    }

    private BaseResponseV2(ErrorCodeResponse code, T data) {
        this.code = code;
        this.data = data;
    }

    public static BaseResponseV2<ErrorCodeResponse> ofSuccess() {
        return new BaseResponseV2<>(BusinessCodeResponse.SUCCESS);
    }

    public static <M> BaseResponseV2<M> ofSuccess(M data) {
        return new BaseResponseV2<>(BusinessCodeResponse.SUCCESS, data);
    }

    public static BaseResponseV2<ErrorCodeResponse> ofFail(BusinessExceptionV2 businessException) {
        return new BaseResponseV2<>(businessException.getErrorCodeResponse(), null);
    }

    public static BaseResponseV2<ErrorCodeResponse> ofFail(AccessDeniedException accessDeniedException) {
        return new BaseResponseV2<>(BusinessCodeResponse.FORBIDDEN, null);
    }

    public static BaseResponseV2<Map<String, String>> ofFail(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> errors = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new BaseResponseV2<>(BusinessCodeResponse.VALIDATE, errors);
    }
}
