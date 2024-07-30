package com.yankong.login.user;

import com.yankong.login.dto.request.DeleteRequestDto;
import com.yankong.login.dto.request.SigninRequestDto;
import com.yankong.login.dto.request.SignupRequestDto;
import com.yankong.login.dto.response.SigninResponseDto;
import com.yankong.login.dto.response.SignupResponseDto;
import com.yankong.login.entity.User;
import com.yankong.login.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    //회원가입이 제대로 되는지
    @Test
    public void testUserRegistration() {
        // Given
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
            .nickname("testuser")
            .username("testuser")
            .password("password")
            .build();
        DeleteRequestDto deleteRequestDto = DeleteRequestDto.builder()
            .password("password")
            .build();

        // When
        SignupResponseDto user = userService.signUp(signupRequestDto);
        User finduser = userRepository.findByUsername("testuser").orElse(null);

        // Then
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("testuser");

        //after
        userService.deleteUser(finduser, deleteRequestDto);
    }

    //로그인 시 토큰이 제대로 발행이 가능한지
    @Test
    public void testUserLogin() {
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

        //after 유저 삭제
        userService.deleteUser(finduser, deleteRequestDto);

        // Then 토큰이 제대로 발행 되는지
        assertThat(user).isNotNull();
        System.out.println("AcessToken : " + user.getAccessToken());
        System.out.println("RefreshToken ; " + user.getRefreshToken());
        assertThat(user.getAccessToken()).isNotNull();
        assertThat(user.getRefreshToken()).isNotNull();
    }
}