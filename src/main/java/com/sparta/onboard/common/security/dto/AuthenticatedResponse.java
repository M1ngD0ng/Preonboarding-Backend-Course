package com.sparta.onboard.common.security.dto;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

import lombok.Getter;

@Getter
public class AuthenticatedResponse {

    private final String token;

    public AuthenticatedResponse(String token) {
        this.token = token;
    }

}
