package com.auction.dto;

// 입찰 요청용 DTO
public class BidCreateDto {

    private Long auctionItemId;
    private Long bidAmount;

    // 생성자
    public BidCreateDto() {
    }

    public BidCreateDto(Long auctionItemId, Long bidAmount) {
        this.auctionItemId = auctionItemId;
        this.bidAmount = bidAmount;
    }

    // Getter and Setter
    public Long getAuctionItemId() {
        return auctionItemId;
    }

    public void setAuctionItemId(Long auctionItemId) {
        this.auctionItemId = auctionItemId;
    }

    public Long getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(Long bidAmount) {
        this.bidAmount = bidAmount;
    }
}
