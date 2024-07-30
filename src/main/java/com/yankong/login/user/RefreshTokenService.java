package com.yankong.login.user;

import com.yankong.login.dto.response.SigninResponseDto;
import com.yankong.login.global.security.jwt.JwtUtil;
import com.yankong.login.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public SigninResponseDto refresh(String refreshToken) {
        //1. refreshToken이 유효한지 확인
        if (!validateToken(refreshToken)) {
            throw new RuntimeException("Refresh Token이 유효하지 않습니다. 다시 로그인 해주세요");
        }
        //2. 해당 유저의 정보를 담은 accessToken을 발급
        String accessToken = jwtUtil.createAccessToken(
            jwtUtil.getUserInfoFromToken(refreshToken).getSubject());
        return new SigninResponseDto(accessToken, refreshToken);
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    public void deleteToken(String username) {
        refreshTokenRepository.deleteByUsername(username);
    }
}
