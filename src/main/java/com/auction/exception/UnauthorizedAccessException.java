package com.auction.exception;

/**
 * 권한이 없는 리소스에 접근할 때 발생하는 예외
 */
public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException(String message) {
        super(message);
    }

    public UnauthorizedAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
