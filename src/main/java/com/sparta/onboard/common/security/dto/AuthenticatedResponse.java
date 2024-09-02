package com.sparta.onboard.common.security.dto;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

import lombok.Getter;

@Getter
public class AuthenticatedResponse {

    private final int statusCode;
    private final String msg;
    private final String accessToken;

    public AuthenticatedResponse(String accessToken, String msg) {
        this.statusCode = SC_OK;
        this.msg = msg;
        this.accessToken = accessToken;
    }

}
