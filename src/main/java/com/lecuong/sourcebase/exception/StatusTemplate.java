package com.lecuong.sourcebase.exception;

import org.springframework.http.HttpStatus;

public interface StatusTemplate {

    StatusResponse SUCCESS =
            new StatusResponse("SHOP-SUCCESS", "SUCCESS", HttpStatus.OK);
    StatusResponse EXPIRE_TOKEN =
            new StatusResponse("SHOP-TOKEN-EXPIRED", "token expired", HttpStatus.UNAUTHORIZED);
    StatusResponse TOKEN_IN_VALID =
            new StatusResponse("SHOP-TOKEN-INVALID", "token invalid", HttpStatus.UNAUTHORIZED);
    StatusResponse USER_NOT_FOUND =
            new StatusResponse("SHOP-USER-NOT-FOUND", "User not found", HttpStatus.NOT_FOUND);
    StatusResponse USER_MUST_LOGIN =
            new StatusResponse("SHOP-USER-MUST-LOGIN", "User must login", HttpStatus.BAD_REQUEST);

    StatusResponse EMPLOYEE_NOT_FOUND =
            new StatusResponse("SHOP-EMPLOYEE-NOT-FOUND", "Employee not found", HttpStatus.NOT_FOUND);
}
