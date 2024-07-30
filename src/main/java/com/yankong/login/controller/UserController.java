package com.yankong.login.controller;

import com.yankong.login.dto.DeleteRequestDto;
import com.yankong.login.dto.UpdateRequestDto;
import com.yankong.login.dto.UserRequestDto;
import com.yankong.login.dto.UserResponseDto;
import com.yankong.login.global.security.CustomUserDetails;
import com.yankong.login.service.UserService;
import jakarta.validation.Valid;
import java.net.URI;
import java.nio.file.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody UserRequestDto userRequestDto) {
        userService.signUp(userRequestDto);
        System.out.println("회원가입 완료");
        return ResponseEntity.created(URI.create("/signup")).build();
    }
    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@Valid @RequestBody UserRequestDto userRequestDto) {
        String accessToken = userService.signIn(userRequestDto);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, accessToken).build();
    }
    @GetMapping("/info")
    public ResponseEntity<UserResponseDto> getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails) throws AccessDeniedException {
        UserResponseDto userResponseDto = userService.getUserInfo(userDetails.getUser());
        return ResponseEntity.ok(userResponseDto);
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody UpdateRequestDto updateRequest) throws AccessDeniedException {
        userService.updateUserInfo(userDetails.getUser(), updateRequest);
        return ResponseEntity.ok("회원정보 수정 완료");
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody DeleteRequestDto deleteRequest) throws AccessDeniedException {
        userService.deleteUser(userDetails.getUser(), deleteRequest);
        return ResponseEntity.ok("회원탈퇴 완료");
    }
}
