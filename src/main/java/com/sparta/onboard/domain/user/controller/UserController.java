package com.sparta.onboard.domain.user.controller;

import com.sparta.onboard.common.security.UserDetailsImpl;
import com.sparta.onboard.common.security.dto.AuthenticatedResponse;
import com.sparta.onboard.domain.user.dto.LoginRequestDto;
import com.sparta.onboard.domain.user.dto.SignupRequestDto;
import com.sparta.onboard.domain.user.dto.SignupResponseDto;
import com.sparta.onboard.domain.user.dto.UserDetailResponseDto;
import com.sparta.onboard.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public SignupResponseDto signup(@Valid @RequestBody SignupRequestDto requestDto) {
        return userService.signup(requestDto);
    }

    @PostMapping("/login")
    public AuthenticatedResponse login(
            @RequestBody LoginRequestDto requestDto,
            HttpServletResponse res){
        return userService.login(requestDto, res);
    }

    @GetMapping("/users")
    public UserDetailResponseDto getUserDetail(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.getUserDetail(userDetails.getUser());
    }
}
