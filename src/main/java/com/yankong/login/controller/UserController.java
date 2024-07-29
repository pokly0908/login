package com.yankong.login.controller;

import com.yankong.login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    //회원가입, 로그인, 회원정보 수정, 회원탈퇴
}
