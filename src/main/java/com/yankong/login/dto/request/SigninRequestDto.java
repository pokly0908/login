package com.yankong.login.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class SigninRequestDto {
    private String password;
    private String username;

}