package com.lecuong.sourcebase.exceptionv2;

import org.springframework.http.HttpStatus;

/**
 * @author CuongLM
 * @created 25/09/2023 - 6:40 PM
 * @project source-base
 */
public interface BusinessCodeResponse {

    ErrorCodeResponse SUCCESS =
            new ErrorCodeResponse("SHOP-SUCCESS", "SUCCESS", HttpStatus.OK);

    /**
     * Exception server
     */
    ErrorCodeResponse FORBIDDEN =
            new ErrorCodeResponse("SHOP-SERVER-FORBIDDEN", "No access to api", HttpStatus.FORBIDDEN);

    /**
     * Exception token
     */
    ErrorCodeResponse EXPIRE_TOKEN =
            new ErrorCodeResponse("SHOP-TOKEN-EXPIRED", "token expired", HttpStatus.UNAUTHORIZED);
    ErrorCodeResponse TOKEN_IN_VALID =
            new ErrorCodeResponse("SHOP-TOKEN-INVALID", "token invalid", HttpStatus.UNAUTHORIZED);

    /**
     * Exception user
     */
    ErrorCodeResponse USER_NOT_FOUND =
            new ErrorCodeResponse("SHOP-USER-NOT-FOUND", "User not found", HttpStatus.NOT_FOUND);
    ErrorCodeResponse USER_MUST_LOGIN =
            new ErrorCodeResponse("SHOP-USER-MUST-LOGIN", "User must login", HttpStatus.BAD_REQUEST);
    ErrorCodeResponse USERNAME_PASSWORD_INCORRECT =
            new ErrorCodeResponse("SHOP-USER", "Username or password incorrect", HttpStatus.NOT_FOUND);

    /**
     * Exception validate user
     */
    ErrorCodeResponse USER_NAME_IS_EMPTY =
            new ErrorCodeResponse("SHOP-VALIDATE-USER", "Username must be not blank and not empty", HttpStatus.BAD_REQUEST);
    ErrorCodeResponse USER_NAME_AVAILABLE =
            new ErrorCodeResponse("SHOP-USER-USER-NAME-AVAILABLE", "Username available", HttpStatus.BAD_REQUEST);
    ErrorCodeResponse EMAIL_AVAILABLE =
            new ErrorCodeResponse("SHOP-USER-EMAIL-AVAILABLE", "Email available", HttpStatus.BAD_REQUEST);
    ErrorCodeResponse PHONE_AVAILABLE =
            new ErrorCodeResponse("SHOP-USER-PHONE-AVAILABLE", "Phone available", HttpStatus.BAD_REQUEST);
    ErrorCodeResponse PASSWORD_IS_EMPTY =
            new ErrorCodeResponse("SHOP-VALIDATE-USER", "Password is empty", HttpStatus.BAD_REQUEST);
    ErrorCodeResponse PASSWORD_INVALIDATE =
            new ErrorCodeResponse("SHOP-VALIDATE-USER", "Password minimum of eight characters, at least one uppercase letter, one lowercase letter, one number, and one special character", HttpStatus.BAD_REQUEST);
    ErrorCodeResponse EMAIL_INVALIDATE =
            new ErrorCodeResponse("SHOP-VALIDATE-USER", "Email is invalid or contains blank", HttpStatus.BAD_REQUEST);
    ErrorCodeResponse PHONE_NUMBER_INVALIDATE =
            new ErrorCodeResponse("SHOP-VALIDATE-USER", "Phone number invalidate", HttpStatus.BAD_REQUEST);
}
