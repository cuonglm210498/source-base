package com.lecuong.sourcebase.exceptionv2;

import lombok.Getter;

/**
 * @author CuongLM
 * @created 25/09/2023 - 6:37 PM
 * @project source-base
 */
@Getter
public class BusinessExceptionV2 extends RuntimeException {

    private ErrorCodeResponse errorCodeResponse;

    public BusinessExceptionV2(ErrorCodeResponse errorCodeResponse) {
        this.errorCodeResponse = errorCodeResponse;
    }
}
