package com.yankong.login.controller;

import com.yankong.login.dto.response.SigninResponseDto;
import com.yankong.login.global.security.jwt.JwtUtil;
import com.yankong.login.user.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RefreshController {

    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    @GetMapping("/refresh")
    public ResponseEntity<SigninResponseDto> refresh(@RequestHeader String refreshToken) {
        SigninResponseDto signinResponse = refreshTokenService.refresh(refreshToken);

        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, signinResponse.getAccessToken())
            .header("RefreshToken", signinResponse.getRefreshToken())
            .body(signinResponse);
    }
}
