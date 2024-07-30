package com.yankong.login.dto.response;

import lombok.Getter;

@Getter
public class SigninResponseDto {
    private String token;

    public SigninResponseDto(String token) {
        this.token = token;
    }


}
