package com.auction.dto;

import com.auction.entity.UserRating;
import java.time.LocalDateTime;

public class UserRatingDto {

    private Long ratingId;
    private Long ratedUserId;
    private String ratedUsername;
    private Long raterUserId;
    private String raterUsername;
    private Integer rating;
    private String comment;
    private String ratingType;
    private Long auctionId;
    private LocalDateTime createdAt;

    // 생성자
    public UserRatingDto() {
    }

    // Entity에서 DTO로 변환
    public static UserRatingDto fromEntity(UserRating rating) {
        UserRatingDto dto = new UserRatingDto();
        dto.setRatingId(rating.getRatingId());

        if (rating.getRatedUser() != null) {
            dto.setRatedUserId(rating.getRatedUser().getId());
            dto.setRatedUsername(rating.getRatedUser().getUsername());
        }

        if (rating.getRaterUser() != null) {
            dto.setRaterUserId(rating.getRaterUser().getId());
            dto.setRaterUsername(rating.getRaterUser().getUsername());
        }

        dto.setRating(rating.getRating());
        dto.setComment(rating.getComment());

        if (rating.getRatingType() != null) {
            dto.setRatingType(rating.getRatingType().name());
        }

        dto.setAuctionId(rating.getAuctionId());
        dto.setCreatedAt(rating.getCreatedAt());

        return dto;
    }

    // Getter and Setter
    public Long getRatingId() {
        return ratingId;
    }

    public void setRatingId(Long ratingId) {
        this.ratingId = ratingId;
    }

    public Long getRatedUserId() {
        return ratedUserId;
    }

    public void setRatedUserId(Long ratedUserId) {
        this.ratedUserId = ratedUserId;
    }

    public String getRatedUsername() {
        return ratedUsername;
    }

    public void setRatedUsername(String ratedUsername) {
        this.ratedUsername = ratedUsername;
    }

    public Long getRaterUserId() {
        return raterUserId;
    }

    public void setRaterUserId(Long raterUserId) {
        this.raterUserId = raterUserId;
    }

    public String getRaterUsername() {
        return raterUsername;
    }

    public void setRaterUsername(String raterUsername) {
        this.raterUsername = raterUsername;
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

    public String getRatingType() {
        return ratingType;
    }

    public void setRatingType(String ratingType) {
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