package com.example.timesheet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UN_EXCEPTION(9999, "Un exception", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1004, "User not existed", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(1002, "Username must be at least 8 characrters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1003, "Password must be at least 8 characrters", HttpStatus.BAD_REQUEST),
    UN_AUTHENTICATIED(1005, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UN_AUTHORIZED(1006, "You do not have permission", HttpStatus.FORBIDDEN),
    TIME_SHEET_DAY_ALREADY_EXISTS(1007, "TIME_SHEET_DAY_ALREADY_EXISTS", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
