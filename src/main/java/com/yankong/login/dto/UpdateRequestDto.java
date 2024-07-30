package com.yankong.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateRequestDto {
    private String oldPassword;
    private String newPassword;
    private String newNickname;
}
