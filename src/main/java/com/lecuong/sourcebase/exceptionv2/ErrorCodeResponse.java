package com.lecuong.sourcebase.exceptionv2;

import lombok.*;
import org.springframework.http.HttpStatus;

/**
 * @author CuongLM
 * @created 25/09/2023 - 6:38 PM
 * @project source-base
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorCodeResponse {

    private String code;
    private String message;
    private HttpStatus status;
}
