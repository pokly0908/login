package com.yankong.login.controller;

import com.yankong.login.dto.request.DeleteRequestDto;
import com.yankong.login.dto.request.SigninRequestDto;
import com.yankong.login.dto.response.SigninResponseDto;
import com.yankong.login.dto.request.UpdateRequestDto;
import com.yankong.login.dto.request.SignupRequestDto;
import com.yankong.login.dto.response.SignupResponseDto;
import com.yankong.login.dto.response.UserResponseDto;
import com.yankong.login.global.security.CustomUserDetails;
import com.yankong.login.user.UserService;
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
    public ResponseEntity<SignupResponseDto> signUp(@Valid @RequestBody SignupRequestDto signupRequest) {
        SignupResponseDto signupResponse = userService.signUp(signupRequest);
        return ResponseEntity.created(URI.create("/signup"))
            .body(signupResponse);
    }
    @PostMapping("/signin")
    public ResponseEntity<SigninResponseDto> signIn(@Valid @RequestBody SigninRequestDto signinRequest) {
        SigninResponseDto signinResponse = userService.signIn(signinRequest);
        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, signinResponse.getAccessToken())
            .header("RefreshToken", signinResponse.getRefreshToken())
            .body(signinResponse);
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
