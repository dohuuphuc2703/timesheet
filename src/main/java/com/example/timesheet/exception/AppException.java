package com.example.timesheet.exception;

public class AppException extends RuntimeException {
    private ErrorCode errorCode;

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
