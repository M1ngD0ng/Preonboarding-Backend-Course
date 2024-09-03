package com.sparta.onboard.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorType {

    // JWT
    INVALID_REFRESH_TOKEN(UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다. 다시 로그인 해주세요."),

    // User
    DUPLICATED_USERNAME(BAD_REQUEST, "이미 존재하는 사용자 아이디입니다.")

    ;

    private final HttpStatus httpStatus;
    private final String message;
}
