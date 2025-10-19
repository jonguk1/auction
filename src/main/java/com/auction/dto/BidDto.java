package com.auction.dto;

import com.auction.entity.Bid;

import java.time.LocalDateTime;

public class BidDto {

    private Long id;
    private Long bidderId;
    private String bidderName;
    private Long auctionItemId;
    private String auctionItemTitle;
    private Long bidAmount;
    private LocalDateTime bidTime;
    private String status;

    // 생성자
    public BidDto() {
    }

    // Entity에서 DTO로 변환하는 정적 메서드
    public static BidDto fromEntity(Bid bid) {
        BidDto dto = new BidDto();
        dto.setId(bid.getId());

        if (bid.getBidder() != null) {
            dto.setBidderId(bid.getBidder().getId());
            dto.setBidderName(bid.getBidder().getUsername());
        }

        if (bid.getAuctionItem() != null) {
            dto.setAuctionItemId(bid.getAuctionItem().getId());
            dto.setAuctionItemTitle(bid.getAuctionItem().getTitle());
        }

        dto.setBidAmount(bid.getBidAmount());
        dto.setBidTime(bid.getBidTime());

        if (bid.getStatus() != null) {
            dto.setStatus(bid.getStatus().name());
        }

        return dto;
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBidderId() {
        return bidderId;
    }

    public void setBidderId(Long bidderId) {
        this.bidderId = bidderId;
    }

    public String getBidderName() {
        return bidderName;
    }

    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    public Long getAuctionItemId() {
        return auctionItemId;
    }

    public void setAuctionItemId(Long auctionItemId) {
        this.auctionItemId = auctionItemId;
    }

    public String getAuctionItemTitle() {
        return auctionItemTitle;
    }

    public void setAuctionItemTitle(String auctionItemTitle) {
        this.auctionItemTitle = auctionItemTitle;
    }

    public Long getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(Long bidAmount) {
        this.bidAmount = bidAmount;
    }

    public LocalDateTime getBidTime() {
        return bidTime;
    }

    public void setBidTime(LocalDateTime bidTime) {
        this.bidTime = bidTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
