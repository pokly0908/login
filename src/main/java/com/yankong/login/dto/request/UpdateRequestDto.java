package com.yankong.login.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateRequestDto {

    private String oldPassword;
    private String newPassword;
    private String newNickname;
}
