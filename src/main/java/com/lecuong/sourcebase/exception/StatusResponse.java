package com.lecuong.sourcebase.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusResponse {

    private String code;
    private String message;
    private HttpStatus status;
}
