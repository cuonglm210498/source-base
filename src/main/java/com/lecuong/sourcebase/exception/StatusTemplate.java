package com.lecuong.sourcebase.exception;

import com.lecuong.sourcebase.constant.MessageConstant;
import org.springframework.http.HttpStatus;

public interface StatusTemplate {

    StatusResponse SUCCESS =
            new StatusResponse("SHOP-SUCCESS", "SUCCESS", HttpStatus.OK);

    /**
     * Exception server
     */
    StatusResponse FORBIDDEN =
            new StatusResponse("SHOP-SERVER-FORBIDDEN", "No access to api", HttpStatus.FORBIDDEN);

    /**
     * Exception token
     */
    StatusResponse EXPIRE_TOKEN =
            new StatusResponse("SHOP-TOKEN-EXPIRED", "token expired", HttpStatus.UNAUTHORIZED);
    StatusResponse TOKEN_IN_VALID =
            new StatusResponse("SHOP-TOKEN-INVALID", "token invalid", HttpStatus.UNAUTHORIZED);

    /**
     * Exception user
     */
    StatusResponse USER_NOT_FOUND =
            new StatusResponse("SHOP-USER-NOT-FOUND", "User not found", HttpStatus.NOT_FOUND);
    StatusResponse USER_MUST_LOGIN =
            new StatusResponse("SHOP-USER-MUST-LOGIN", "User must login", HttpStatus.BAD_REQUEST);

    /**
     * Exception validate user
     */
    StatusResponse USER_NAME_IS_EMPTY =
            new StatusResponse("SHOP-VALIDATE-USER", "Username must be not blank and not empty", HttpStatus.BAD_REQUEST);
    StatusResponse USER_NAME_AVAILABLE =
            new StatusResponse("SHOP-USER-USER-NAME-AVAILABLE", "Username available", HttpStatus.BAD_REQUEST);
    StatusResponse EMAIL_AVAILABLE =
            new StatusResponse("SHOP-USER-EMAIL-AVAILABLE", "Email available", HttpStatus.BAD_REQUEST);
    StatusResponse PHONE_AVAILABLE =
            new StatusResponse("SHOP-USER-PHONE-AVAILABLE", "Phone available", HttpStatus.BAD_REQUEST);
    StatusResponse PASSWORD_IS_EMPTY =
            new StatusResponse("SHOP-VALIDATE-USER", "Password is empty", HttpStatus.BAD_REQUEST);
    StatusResponse PASSWORD_INVALIDATE =
            new StatusResponse("SHOP-VALIDATE-USER", "Password minimum of eight characters, at least one uppercase letter, one lowercase letter, one number, and one special character", HttpStatus.BAD_REQUEST);
    StatusResponse EMAIL_INVALIDATE =
            new StatusResponse("SHOP-VALIDATE-USER", "Email is invalid or contains blank", HttpStatus.BAD_REQUEST);
    StatusResponse PHONE_NUMBER_INVALIDATE =
            new StatusResponse("SHOP-VALIDATE-USER", "Phone number invalidate", HttpStatus.BAD_REQUEST);

    /**
     * Exception employee
     */
    StatusResponse EMPLOYEE_NOT_FOUND =
            new StatusResponse("SHOP-EMPLOYEE-NOT-FOUND", "Employee not found", HttpStatus.NOT_FOUND);

    /**
     * Exception AttackFile
     */
    StatusResponse FILE_ERROR =
            new StatusResponse("SHOP-FILE-OPERATION", "Error when operation with file", HttpStatus.BAD_REQUEST);
    StatusResponse FILE_INVALID_FORMAT =
            new StatusResponse("SHOP-FILE-INVALID_FORMAT", "Invalid file format. You need to choose another file type! Satisfying file formats include: excel, pdf, word, mp3, mp4, rar, zip, image file", HttpStatus.BAD_REQUEST);
    StatusResponse FILE_NOT_FOUND =
            new StatusResponse("SHOP-FILE-NOT-FOUND", "File not found", HttpStatus.NOT_FOUND);
    StatusResponse FILE_MANIPULATION_FAILED =
            new StatusResponse("SHOP-FILE-MANIPULATION-FAILED", "File manipulation failed", HttpStatus.BAD_REQUEST);
}
