package com.yankong.login.entity;

import com.yankong.login.dto.request.UpdateRequestDto;
import com.yankong.login.dto.request.SignupRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private UserRole role = UserRole.ADMIN;

    public User(SignupRequestDto userRequest, String encodedPassword) {
        this.username = userRequest.getUsername();
        this.password = encodedPassword;
        this.nickname = userRequest.getNickname();
    }

    public static User from(
        SignupRequestDto userRequest,
        String encodedPassword

    ) {
        return new User(userRequest, encodedPassword);
    }

    public void update(UpdateRequestDto updateRequest, String encodedPassword) {
        this.password = encodedPassword;
        this.nickname = updateRequest.getNewNickname();
    }
}