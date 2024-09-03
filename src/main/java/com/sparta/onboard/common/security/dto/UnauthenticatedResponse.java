package com.sparta.onboard.common.security.dto;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import lombok.Getter;

@Getter
public class UnauthenticatedResponse {

    private final int statusCode;
    private final String msg;

    public UnauthenticatedResponse(String msg) {
        this.statusCode = SC_UNAUTHORIZED;
        this.msg = msg;
    }

}
