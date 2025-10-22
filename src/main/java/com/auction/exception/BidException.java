package com.auction.exception;

/**
 * 입찰 관련 예외 (입찰가 부족, 자기 경매 입찰 등)
 */
public class BidException extends RuntimeException {

    public BidException(String message) {
        super(message);
    }

    public BidException(String message, Throwable cause) {
        super(message, cause);
    }
}
