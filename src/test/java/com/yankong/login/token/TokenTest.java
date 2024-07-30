package com.yankong.login.token;

import com.yankong.login.dto.request.DeleteRequestDto;
import com.yankong.login.dto.request.SigninRequestDto;
import com.yankong.login.dto.request.SignupRequestDto;
import com.yankong.login.dto.response.SigninResponseDto;
import com.yankong.login.entity.User;
import com.yankong.login.global.security.jwt.JwtUtil;
import com.yankong.login.repository.RefreshTokenRepository;
import com.yankong.login.repository.UserRepository;
import com.yankong.login.user.RefreshTokenService;
import com.yankong.login.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@SpringBootTest
@ActiveProfiles("test")
public class TokenTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtUtil jwtUtil;

    //토큰이 유효하지 않을 때
    @Test
    public void testTokenExpiration() {
        // Given
        String token = "expiredToken";
        when(jwtUtil.validateToken(token)).thenReturn(false);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> refreshTokenService.refresh(token));
        assertThat(exception.getMessage()).isEqualTo("Refresh Token이 유효하지 않습니다. 다시 로그인 해주세요");
    }

    //RefreshToken이 유효할 때 AccessToken 재 발행
    //과거 AccessToken과 현재 AccessToken이 다름을 테스트
    //로그인 시 토큰이 제대로 발행이 가능한지
    @Test
    public void testRefresh() {
        // Given
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
            .nickname("testuser")
            .username("testuser")
            .password("password")
            .build();
        SigninRequestDto signinRequestDto = SigninRequestDto.builder()
            .username("testuser")
            .password("password")
            .build();
        DeleteRequestDto deleteRequestDto = DeleteRequestDto.builder()
            .password("password")
            .build();
        userService.signUp(signupRequestDto);


        // When
        SigninResponseDto user = userService.signIn(signinRequestDto);
        User finduser = userRepository.findByUsername("testuser").orElse(null);

        // Then 토큰이 제대로 발행 되는지
        assertThat(user).isNotNull();
        System.out.println("RefreshToken ; " + user.getRefreshToken());
        System.out.println("OldAcessToken : " + user.getAccessToken());
        System.out.println("NewAcessToken : " + userService.signIn(signinRequestDto).getAccessToken());
        assertThat(user.getAccessToken()).isNotNull();
        assertThat(user.getRefreshToken()).isNotNull();
        userService.deleteUser(finduser, deleteRequestDto);
    }
}