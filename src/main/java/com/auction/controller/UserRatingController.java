package com.auction.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auction.dto.UserRatingDto;
import com.auction.service.UserRatingService;

@RestController
@RequestMapping("/auction/user-ratings")
public class UserRatingController {

    public final UserRatingService userRatingService;

    public UserRatingController(UserRatingService userRatingService) {
        this.userRatingService = userRatingService;
    }

    // 사용자가 받은 평점들 조회
    @GetMapping("/{userId}")
    public List<UserRatingDto> getUserRatings(@PathVariable Long userId) {
        return userRatingService.getUserRatings(userId);
    }

    // 사용자의 평균 평점 계산
    @GetMapping("/{userId}/average")
    public Double getAverageRating(@PathVariable Long userId) {
        return userRatingService.getAverageRating(userId);
    }

}
