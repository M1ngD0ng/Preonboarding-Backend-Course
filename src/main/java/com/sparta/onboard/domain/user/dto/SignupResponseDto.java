package com.sparta.onboard.domain.user.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupResponseDto {
    private String username;
    private String nickname;
    private List<AuthorityDto> authorities;

    @Getter
    @AllArgsConstructor
    public static class AuthorityDto {
        private String authorityName;
    }
}
