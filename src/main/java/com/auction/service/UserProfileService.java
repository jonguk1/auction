package com.auction.service;

import org.springframework.stereotype.Service;

import com.auction.dto.UserProfileDto;
import com.auction.entity.UserProfile;
import com.auction.exception.UserNotFoundException;
import com.auction.repository.UserProfileRepository;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    // 사용자로 프로필 찾기
    public UserProfileDto getUserProfileByUserId(Long userId) {
        UserProfile userProfile = userProfileRepository.findByUser_Id(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자 프로필을 찾을 수 없습니다. ID: " + userId));
        return UserProfileDto.fromEntity(userProfile);
    }

}
