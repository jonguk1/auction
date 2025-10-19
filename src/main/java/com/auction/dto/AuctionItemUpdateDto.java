package com.auction.dto;

import java.time.LocalDateTime;

// 경매 상품 수정용 DTO
public class AuctionItemUpdateDto {

    private String title;
    private String description;
    private LocalDateTime endTime; // 종료 시간만 연장 가능

    // 생성자
    public AuctionItemUpdateDto() {
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

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
