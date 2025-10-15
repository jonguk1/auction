package com.auction.controller;

import com.auction.dto.UserLoginDto;
import com.auction.dto.UserRegisterDto;
import com.auction.dto.UserResponseDto;
import com.auction.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auction/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        try {
            boolean success = userService.registerUser(userRegisterDto);

            if (success) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("회원가입이 성공적으로 완료되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("이미 존재하는 이메일 또는 사용자명입니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("회원가입 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto loginDto) {
        try {
            UserResponseDto userResponse = userService.login(loginDto);

            if (userResponse != null) {
                return ResponseEntity.ok(userResponse);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("이메일 또는 비밀번호가 올바르지 않습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("로그인 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 사용자 ID로 조회
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            UserResponseDto userResponse = userService.getUserById(userId);

            if (userResponse != null) {
                return ResponseEntity.ok(userResponse);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("사용자를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("사용자 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 이메일로 사용자 조회
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        try {
            UserResponseDto userResponse = userService.getUserByEmail(email);

            if (userResponse != null) {
                return ResponseEntity.ok(userResponse);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("사용자를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("사용자 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}