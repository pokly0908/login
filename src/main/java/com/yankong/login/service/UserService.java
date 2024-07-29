package com.yankong.login.service;

import com.yankong.login.dto.UserRequestDto;
import com.yankong.login.dto.UserResponseDto;
import com.yankong.login.entity.User;
import com.yankong.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void signUp(UserRequestDto userRequestDto) {

    }

    public UserResponseDto signIn(UserRequestDto userRequestDto) {
        return null;
    }

    public UserResponseDto getUserInfo(User user) {
        return null;
    }
}
