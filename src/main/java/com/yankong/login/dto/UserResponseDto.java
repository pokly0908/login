package com.yankong.login.dto;

import com.yankong.login.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String nickname;
    private String password;
    private String username;

    public UserResponseDto(User user) {
        this.nickname = user.getNickname();
        this.password = user.getPassword();
        this.username = user.getUsername();
    }
}
