package com.sparta.onboard.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    @NotBlank(message = "아이디는 공백일 수 없습니다.")
    @Size(min = 6, max = 20, message = "ID는 최소 6자 이상, 최대 20자 이하이어야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "ID는 영문 또는 숫자만 가능합니다.")
    private String username;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "닉네임은 공백일 수 없습니다.")
    private String nickname;
}
