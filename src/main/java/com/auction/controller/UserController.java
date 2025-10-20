package com.auction.controller;

import com.auction.dto.JwtTokenDto;
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
        userService.registerUser(userRegisterDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("회원가입이 성공적으로 완료되었습니다.");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody UserLoginDto loginDto) {
        JwtTokenDto tokenDto = userService.login(loginDto);
        return ResponseEntity.ok(tokenDto);
    }

    // 사용자 ID로 조회
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {
        UserResponseDto userResponse = userService.getUserById(userId);
        return ResponseEntity.ok(userResponse);
    }

    // 이메일로 사용자 조회
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        UserResponseDto userResponse = userService.getUserByEmail(email);
        return ResponseEntity.ok(userResponse);
    }
}