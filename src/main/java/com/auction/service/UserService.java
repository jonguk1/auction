package com.auction.service;

import com.auction.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auction.dto.UserLoginDto;
import com.auction.dto.UserRegisterDto;
import com.auction.dto.UserResponseDto;
import com.auction.entity.UserProfile;
import com.auction.repository.UserProfileRepository;
import com.auction.repository.UserRatingRepository;
import com.auction.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserRatingRepository userRatingRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, UserProfileRepository userProfileRepository,
            UserRatingRepository userRatingRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.userRatingRepository = userRatingRepository;
    }

    // 회원가입
    public boolean registerUser(UserRegisterDto userRegisterDto) {
        // 🔍 이메일 중복 체크
        if (userRepository.existsByEmail(userRegisterDto.getEmail())) {
            return false; // Email already exists
        }

        // 🔍 사용자명 중복 체크
        if (userRepository.existsByUsername(userRegisterDto.getUsername())) {
            return false; // Username already exists
        }

        // 💡 DTO의 변환 메서드 활용
        User user = userRegisterDto.toEntity();

        // 🔐 비밀번호 암호화 (DTO 변환 후 덮어쓰기)
        String encodedPassword = passwordEncoder.encode(userRegisterDto.getPassword());
        user.setPassword(encodedPassword);

        // 💾 사용자 저장
        userRepository.save(user);

        // 👨‍💼 기본 프로필 생성 (선택사항)
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfileRepository.save(userProfile);

        return true;
    }

    // 로그인
    public UserResponseDto login(UserLoginDto loginDto) {
        // 이메일로 사용자 찾기
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElse(null);

        if (user == null) {
            return null; // 사용자 없음
        }

        // 비밀번호 확인
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return null; // 비밀번호 틀림
        }

        // 로그인 성공 시 마지막 로그인 시간 업데이트
        user.updateLastLoginAt();
        userRepository.save(user);

        // DTO로 변환해서 반환 (비밀번호 제외)
        return UserResponseDto.fromEntity(user);
    }

    // 사용자 ID로 조회
    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }

        UserResponseDto responseDto = UserResponseDto.fromEntity(user);

        // 평균 평점 추가
        Double averageRating = userRatingRepository.findAverageRatingByUserId(userId)
                .orElse(0.0);
        responseDto.setAverageRating(averageRating);

        return responseDto;
    }

    // 이메일로 사용자 조회
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return null;
        }
        return UserResponseDto.fromEntity(user);
    }

}
