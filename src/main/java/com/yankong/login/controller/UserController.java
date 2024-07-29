package com.yankong.login.controller;

import com.yankong.login.dto.UserRequestDto;
import com.yankong.login.dto.UserResponseDto;
import com.yankong.login.global.security.CustomUserDetails;
import com.yankong.login.service.UserService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@Valid @RequestBody UserRequestDto userRequestDto) {
        userService.signUp(userRequestDto);
        return ResponseEntity.created(URI.create("/users")).build();
    }
    @PostMapping("/signIn")
    public ResponseEntity<UserResponseDto> signIn(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.signIn(userRequestDto);
        return ResponseEntity.ok(userResponseDto);
    }
    @GetMapping("/info")
    public ResponseEntity<UserResponseDto> getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserResponseDto userResponseDto = userService.getUserInfo(userDetails.getUser());
        return ResponseEntity.ok(userResponseDto);
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateUserInfo() {
        //회원정보 수정
        return ResponseEntity.ok("회원정보 수정 완료");
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser() {
        //회원탈퇴
        return ResponseEntity.ok("회원탈퇴 완료");
    }
}
