package com.yankong.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserRequestDto {
    private String nickname;
    private String password;
    private String name;


}
