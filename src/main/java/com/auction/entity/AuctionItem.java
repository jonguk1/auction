package com.auction.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auction_items")
public class AuctionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 판매자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    // 상품 정보
    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    // 가격 정보
    @Column(nullable = false)
    private Long startPrice; // 시작가

    @Column(nullable = false)
    private Long currentPrice; // 현재 최고가

    // 경매 시간
    @Column(nullable = false)
    private LocalDateTime startTime; // 경매 시작 시간

    @Column(nullable = false)
    private LocalDateTime endTime; // 경매 종료 시간

    // 경매 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuctionStatus status = AuctionStatus.PENDING;

    // 입찰 내역 (양방향 관계)
    @OneToMany(mappedBy = "auctionItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bid> bids = new ArrayList<>();

    // 생성/수정 시간
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // 경매 상태 Enum
    public enum AuctionStatus {
        PENDING, // 대기중 (경매 시작 전)
        ACTIVE, // 진행중
        CLOSED, // 종료됨
        AWARDED // 낙찰 완료
    }

    // 생성자
    public AuctionItem() {
    }

    // JPA 콜백 메서드
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        // 현재가를 시작가로 초기화
        if (this.currentPrice == null) {
            this.currentPrice = this.startPrice;
        }

        // 상태 초기화
        if (this.status == null) {
            this.status = AuctionStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // 비즈니스 로직

    // 경매가 진행중인지 확인
    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return this.status == AuctionStatus.ACTIVE
                && now.isAfter(this.startTime)
                && now.isBefore(this.endTime);
    }

    // 경매 시작
    public void start() {
        if (this.status == AuctionStatus.PENDING) {
            this.status = AuctionStatus.ACTIVE;
        }
    }

    // 경매 종료
    public void close() {
        if (this.status == AuctionStatus.ACTIVE) {
            this.status = AuctionStatus.CLOSED;
        }
    }

    // 낙찰 처리
    public void award() {
        if (this.status == AuctionStatus.CLOSED) {
            this.status = AuctionStatus.AWARDED;
        }
    }

    // 현재가 업데이트
    public void updateCurrentPrice(Long newPrice) {
        if (newPrice > this.currentPrice) {
            this.currentPrice = newPrice;
        }
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
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

    public AuctionStatus getStatus() {
        return status;
    }

    public void setStatus(AuctionStatus status) {
        this.status = status;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
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
