package com.yankong.login.dto.response;

import com.yankong.login.entity.User;
import com.yankong.login.entity.UserRole;
import java.util.ArrayList;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class SignupResponseDto {
    private String username;
    private String nickname;
    private List<Authority> authorities;

    public SignupResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.authorities = new ArrayList<>();
        this.authorities.add(new Authority(user));
    }

    @Getter
    public static class Authority {
        private String authorityName;

        public Authority(User user) {
            this.authorityName = user.getRole().getAuthority();
        }
    }
}