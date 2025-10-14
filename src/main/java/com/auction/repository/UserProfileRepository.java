package com.auction.repository;

import com.auction.entity.UserProfile;
import com.auction.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    // 사용자로 프로필 찾기
    Optional<UserProfile> findByUser(User user);

    // 사용자 ID로 프로필 찾기
    @Query("SELECT up FROM UserProfile up WHERE up.user.id = :userId")
    Optional<UserProfile> findByUserId(@Param("userId") Long userId);
}