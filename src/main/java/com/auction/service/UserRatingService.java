package com.auction.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.dto.UserRatingDto;
import com.auction.entity.User;
import com.auction.entity.UserRating;
import com.auction.repository.UserRatingRepository;

@Service
public class UserRatingService {

    private final UserService userService;
    private final UserRatingRepository userRatingRepository;

    public UserRatingService(UserService userService,
            UserRatingRepository userRatingRepository) {
        this.userService = userService;
        this.userRatingRepository = userRatingRepository;
    }

    // 사용자가 받은 평점들 조회
    @Transactional(readOnly = true)
    public List<UserRatingDto> getUserRatings(Long userId) {
        User user = userService.getUserEntityById(userId);
        List<UserRating> ratings = userRatingRepository.findByRatedUser(user);
        return ratings.stream()
                .map(UserRatingDto::fromEntity)
                .toList();
    }

    // 사용자의 평균 평점 계산
    @Transactional(readOnly = true)
    public Double getAverageRating(Long userId) {
        return userRatingRepository.findAverageRatingByUserId(userId)
                .orElse(0.0);
    }
}
