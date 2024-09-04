package com.sparta.onboard.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDetailResponseDto {
    private String username;
    private String nickname;
    private String role;
}
