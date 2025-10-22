package com.auction.exception;

/**
 * 경매를 찾을 수 없을 때 발생하는 예외
 */
public class AuctionNotFoundException extends RuntimeException {

    public AuctionNotFoundException(String message) {
        super(message);
    }

    public AuctionNotFoundException(Long auctionId) {
        super("경매를 찾을 수 없습니다: " + auctionId);
    }

    public AuctionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
