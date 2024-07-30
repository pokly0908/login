package com.yankong.login.dto.response;

import com.yankong.login.entity.RefreshToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RefreshResponseDto {

    private String token;

    public RefreshResponseDto(String token) {
        this.token = token;
    }

}
