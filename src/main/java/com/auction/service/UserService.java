package com.auction.service;

import com.auction.config.JwtTokenProvider;
import com.auction.dto.JwtTokenDto;
import com.auction.dto.UserLoginDto;
import com.auction.dto.UserRegisterDto;
import com.auction.dto.UserResponseDto;
import com.auction.entity.User;
import com.auction.entity.UserProfile;
import com.auction.exception.AuthenticationFailedException;
import com.auction.exception.DuplicateUserException;
import com.auction.exception.UserNotFoundException;
import com.auction.repository.UserProfileRepository;
import com.auction.repository.UserRatingRepository;
import com.auction.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserRatingRepository userRatingRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository,
            UserProfileRepository userProfileRepository,
            UserRatingRepository userRatingRepository,
            BCryptPasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.userRatingRepository = userRatingRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 회원가입
    public void registerUser(UserRegisterDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateUserException("이미 등록된 이메일입니다.");
        }

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new DuplicateUserException("이미 사용 중인 사용자명입니다.");
        }

        User user = dto.toEntity();
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // 기본 역할 설정 (BUYER)
        if (user.getUserRole() == null) {
            user.setUserRole(User.UserRole.BUYER);
        }

        userRepository.save(user);

        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfileRepository.save(userProfile);
    }

    // 로그인
    public JwtTokenDto login(UserLoginDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new AuthenticationFailedException("아이디 또는 비밀번호가 잘못되었습니다."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new AuthenticationFailedException("아이디 또는 비밀번호가 잘못되었습니다.");
        }

        user.updateLastLoginAt();
        userRepository.save(user);

        // JWT 토큰 생성
        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        return new JwtTokenDto(
                "Bearer",
                accessToken,
                refreshToken,
                60 * 60L // 1시간 (초 단위)
        );
    }

    // 사용자 ID로 조회
    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 사용자를 찾을 수 없습니다."));

        Double averageRating = userRatingRepository.findAverageRatingByUserId(userId).orElse(0.0);

        UserResponseDto responseDto = UserResponseDto.fromEntity(user);
        responseDto.setAverageRating(averageRating);

        return responseDto;
    }

    // 이메일로 사용자 조회
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("해당 사용자를 찾을 수 없습니다."));
        return UserResponseDto.fromEntity(user);
    }

    // 내부용: 엔티티 직접 반환
    public User getUserEntityById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 사용자를 찾을 수 없습니다."));
    }
}
