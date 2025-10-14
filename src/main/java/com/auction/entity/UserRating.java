package com.auction.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_ratings")
public class UserRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rated_user_id", referencedColumnName = "id")
    private User ratedUser; // 평가받는 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rater_user_id", referencedColumnName = "id")
    private User raterUser; // 평가하는 사용자

    @Column(nullable = false)
    private Integer rating; // 1-5점

    @Column(columnDefinition = "TEXT")
    private String comment;

    public enum RatingType {
        SELLER, BUYER
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "rating_type", nullable = false)
    private RatingType ratingType;

    @Column(name = "auction_id")
    private Long auctionId; // 관련 경매 ID (나중에 Auction 엔티티와 연결)

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Getter and Setter methods
    public Long getRatingId() {
        return ratingId;
    }

    public void setRatingId(Long ratingId) {
        this.ratingId = ratingId;
    }

    public User getRatedUser() {
        return ratedUser;
    }

    public void setRatedUser(User ratedUser) {
        this.ratedUser = ratedUser;
    }

    public User getRaterUser() {
        return raterUser;
    }

    public void setRaterUser(User raterUser) {
        this.raterUser = raterUser;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public RatingType getRatingType() {
        return ratingType;
    }

    public void setRatingType(RatingType ratingType) {
        this.ratingType = ratingType;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}