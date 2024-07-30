package com.yankong.login.dto.response;

import com.yankong.login.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String nickname;
    private String username;

    public UserResponseDto(User user) {
        this.nickname = user.getNickname();
        this.username = user.getUsername();
    }
}
