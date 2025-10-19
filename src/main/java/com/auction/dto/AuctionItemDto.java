package com.auction.dto;

import com.auction.entity.AuctionItem;

import java.time.LocalDateTime;

public class AuctionItemDto {

    private Long id;
    private Long sellerId;
    private String sellerName;
    private String title;
    private String description;
    private Long startPrice;
    private Long currentPrice;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private Integer bidCount; // 입찰 개수
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 생성자
    public AuctionItemDto() {
    }

    // Entity에서 DTO로 변환하는 정적 메서드
    public static AuctionItemDto fromEntity(AuctionItem auctionItem) {
        AuctionItemDto dto = new AuctionItemDto();
        dto.setId(auctionItem.getId());

        if (auctionItem.getSeller() != null) {
            dto.setSellerId(auctionItem.getSeller().getId());
            dto.setSellerName(auctionItem.getSeller().getUsername());
        }

        dto.setTitle(auctionItem.getTitle());
        dto.setDescription(auctionItem.getDescription());
        dto.setStartPrice(auctionItem.getStartPrice());
        dto.setCurrentPrice(auctionItem.getCurrentPrice());
        dto.setStartTime(auctionItem.getStartTime());
        dto.setEndTime(auctionItem.getEndTime());

        if (auctionItem.getStatus() != null) {
            dto.setStatus(auctionItem.getStatus().name());
        }

        dto.setCreatedAt(auctionItem.getCreatedAt());
        dto.setUpdatedAt(auctionItem.getUpdatedAt());

        return dto;
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Long startPrice) {
        this.startPrice = startPrice;
    }

    public Long getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Long currentPrice) {
        this.currentPrice = currentPrice;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getBidCount() {
        return bidCount;
    }

    public void setBidCount(Integer bidCount) {
        this.bidCount = bidCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
