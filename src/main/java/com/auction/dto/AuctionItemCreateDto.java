package com.auction.dto;

import java.time.LocalDateTime;

// 경매 상품 등록용 DTO
public class AuctionItemCreateDto {

    private String title;
    private String description;
    private Long startPrice;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // 생성자
    public AuctionItemCreateDto() {
    }

    public AuctionItemCreateDto(String title, String description, Long startPrice,
            LocalDateTime startTime, LocalDateTime endTime) {
        this.title = title;
        this.description = description;
        this.startPrice = startPrice;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getter and Setter
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
}
