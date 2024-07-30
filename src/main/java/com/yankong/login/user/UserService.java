package com.yankong.login.user;

import com.yankong.login.dto.request.DeleteRequestDto;
import com.yankong.login.dto.request.SigninRequestDto;
import com.yankong.login.dto.request.UpdateRequestDto;
import com.yankong.login.dto.request.SignupRequestDto;
import com.yankong.login.dto.response.SigninResponseDto;
import com.yankong.login.dto.response.SignupResponseDto;
import com.yankong.login.dto.response.UserResponseDto;
import com.yankong.login.entity.RefreshToken;
import com.yankong.login.entity.User;
import com.yankong.login.global.security.jwt.JwtUtil;
import com.yankong.login.repository.RefreshTokenRepository;
import com.yankong.login.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public SignupResponseDto signUp(SignupRequestDto signupRequestDto) {
        //만약 이미 존재하는 아이디라면 예외처리
        if (userRepository.findByUsername(signupRequestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
        User user = User.from(signupRequestDto, encodedPassword);
        userRepository.save(user);
        return new SignupResponseDto(user);
    }

    @Transactional
    public SigninResponseDto signIn(SigninRequestDto signinRequest) {
        User user;
        try {
            user = userRepository.findByUsername(signinRequest.getUsername())
                .orElseThrow(NoSuchElementException::new);
            if (!passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())) {

                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("아이디 또는 비밀번호가 틀렸습니다.");

        }

        // 해당 유저가 토큰을 가지고 있으면 DB에서 토큰 update
        String refreshToken = jwtUtil.createRefreshToken(user.getUsername());
        refreshTokenRepository.findByUsername(user.getUsername()).ifPresentOrElse(
            refreshTokenEntity -> refreshTokenEntity.update(refreshToken),
            () -> refreshTokenRepository.save(new RefreshToken(user.getUsername(), refreshToken))
        );
        return new SigninResponseDto(jwtUtil.createAccessToken(user.getUsername()), refreshToken);
    }

    public UserResponseDto getUserInfo(User user) {
        User findUser = userRepository.findByUsername(user.getUsername())
            .orElseThrow(IllegalArgumentException::new);
        return new UserResponseDto(findUser);
    }

    @Transactional
    public void updateUserInfo(User user, UpdateRequestDto updateRequest) {
        User findUser = userRepository.findByUsername(user.getUsername())
            .orElseThrow(IllegalArgumentException::new);
        if (!passwordEncoder.matches(updateRequest.getOldPassword(), findUser.getPassword())) {
            throw new IllegalArgumentException();
        }
        String encodedPassword = passwordEncoder.encode(updateRequest.getNewPassword());
        findUser.update(updateRequest, encodedPassword);
    }

    public void deleteUser(User user, DeleteRequestDto deleteRequest) {
        User findUser = userRepository.findByUsername(user.getUsername())
            .orElseThrow(IllegalArgumentException::new);
        if (!passwordEncoder.matches(deleteRequest.getPassword(), findUser.getPassword())) {
            throw new IllegalArgumentException();
        }
        userRepository.delete(findUser);
    }
}
