package com.auction.repository;

import com.auction.entity.UserRating;
import com.auction.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRatingRepository extends JpaRepository<UserRating, Long> {

    // 사용자가 받은 평점들 조회
    List<UserRating> findByRatedUser(User user);

    // 사용자의 평균 평점 계산
    @Query("SELECT AVG(ur.rating) FROM UserRating ur WHERE ur.ratedUser.id = :userId")
    Optional<Double> findAverageRatingByUserId(@Param("userId") Long userId);
}