package com.auction.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bids")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 입찰자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bidder_id", nullable = false)
    private User bidder;

    // 경매 상품
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_item_id", nullable = false)
    private AuctionItem auctionItem;

    // 입찰 금액
    @Column(nullable = false)
    private Long bidAmount;

    // 입찰 시간
    @Column(nullable = false, updatable = false)
    private LocalDateTime bidTime;

    // 입찰 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BidStatus status = BidStatus.ACTIVE;

    // 입찰 상태 Enum
    public enum BidStatus {
        ACTIVE, // 유효한 입찰
        OUTBID, // 다른 사람이 더 높은 가격으로 입찰함
        WINNING, // 현재 최고가 입찰
        LOSING, // 경쟁 중 (다른 사람이 더 높은 가격에 입찰)
        WON, // 낙찰됨
        LOST, // 낙찰 실패
        CANCELLED // 취소됨
    }

    // 생성자
    public Bid() {
    }

    public Bid(User bidder, AuctionItem auctionItem, Long bidAmount) {
        this.bidder = bidder;
        this.auctionItem = auctionItem;
        this.bidAmount = bidAmount;
    }

    // JPA 콜백 메서드
    @PrePersist
    protected void onCreate() {
        this.bidTime = LocalDateTime.now();
        if (this.status == null) {
            this.status = BidStatus.ACTIVE;
        }
    }

    // 비즈니스 로직

    // 입찰이 유효한지 확인
    public boolean isValid() {
        return this.status == BidStatus.ACTIVE || this.status == BidStatus.WINNING;
    }

    // 입찰 취소
    public void cancel() {
        this.status = BidStatus.CANCELLED;
    }

    // 입찰이 밀렸을 때
    public void markAsOutbid() {
        if (this.status == BidStatus.WINNING || this.status == BidStatus.ACTIVE) {
            this.status = BidStatus.OUTBID;
        }
    }

    // 현재 최고가 입찰로 표시
    public void markAsWinning() {
        this.status = BidStatus.WINNING;
    }

    // 낙찰됨으로 표시
    public void markAsWon() {
        if (this.status == BidStatus.WINNING) {
            this.status = BidStatus.WON;
        }
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getBidder() {
        return bidder;
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
    }

    public AuctionItem getAuctionItem() {
        return auctionItem;
    }

    public void setAuctionItem(AuctionItem auctionItem) {
        this.auctionItem = auctionItem;
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

    public BidStatus getStatus() {
        return status;
    }

    public void setStatus(BidStatus status) {
        this.status = status;
    }
}
