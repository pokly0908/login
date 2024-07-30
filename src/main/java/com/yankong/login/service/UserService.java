package com.yankong.login.service;

import com.yankong.login.dto.DeleteRequestDto;
import com.yankong.login.dto.UpdateRequestDto;
import com.yankong.login.dto.UserRequestDto;
import com.yankong.login.dto.UserResponseDto;
import com.yankong.login.entity.User;
import com.yankong.login.global.security.jwt.JwtUtil;
import com.yankong.login.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signUp(UserRequestDto userRequestDto) {
        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
        User user = User.from(userRequestDto, encodedPassword);
        userRepository.save(user);
    }

    @Transactional
    public String signIn(UserRequestDto userRequestDto) {
        User user = userRepository.findByUsername(userRequestDto.getUsername()).orElseThrow(IllegalArgumentException::new);
        if (!passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException();
        }
        return jwtUtil.createAccessToken(user.getUsername());
    }

    public UserResponseDto getUserInfo(User user) {
        User findUser = userRepository.findByUsername(user.getUsername()).orElseThrow(IllegalArgumentException::new);
        return new UserResponseDto(findUser);
    }
    @Transactional
    public void updateUserInfo(User user, UpdateRequestDto updateRequest) {
        User findUser = userRepository.findByUsername(user.getUsername()).orElseThrow(IllegalArgumentException::new);
        if(!passwordEncoder.matches(updateRequest.getOldPassword(), findUser.getPassword())) {
            throw new IllegalArgumentException();
        }
        String encodedPassword = passwordEncoder.encode(updateRequest.getNewPassword());
        findUser.update(updateRequest, encodedPassword);
    }

    public void deleteUser(User user, DeleteRequestDto deleteRequest) {
        User findUser = userRepository.findByUsername(user.getUsername()).orElseThrow(IllegalArgumentException::new);
        if(!passwordEncoder.matches(deleteRequest.getPassword(), findUser.getPassword())) {
            throw new IllegalArgumentException();
        }
        userRepository.delete(findUser);
    }
}
