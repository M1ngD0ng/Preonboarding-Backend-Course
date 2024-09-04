package com.sparta.onboard.domain.user.service;

import com.sparta.onboard.common.exception.CustomException;
import com.sparta.onboard.common.exception.ErrorType;
import com.sparta.onboard.common.security.UserDetailsImpl;
import com.sparta.onboard.domain.user.constant.UserRoleEnum;
import com.sparta.onboard.domain.user.dto.SignupRequestDto;
import com.sparta.onboard.domain.user.dto.SignupResponseDto;
import com.sparta.onboard.domain.user.dto.UserDetailResponseDto;
import com.sparta.onboard.domain.user.entity.User;
import com.sparta.onboard.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto requestDto) {
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new CustomException(
                    ErrorType.DUPLICATED_USERNAME);
        }
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User savedUser = userRepository.save(
                User.of(requestDto.getUsername(),
                        encodedPassword,
                        requestDto.getNickname(),
                        UserRoleEnum.ROLE_USER));

        // UserDetailsImpl 생성하여 권한 정보 가져옴
        UserDetailsImpl userDetails = new UserDetailsImpl(savedUser);
        List<SignupResponseDto.AuthorityDto> authorities = userDetails.getAuthorities().stream()
                .map(authority -> new SignupResponseDto.AuthorityDto(authority.getAuthority()))
                .toList();
        return new SignupResponseDto(
                savedUser.getUsername(),
                savedUser.getNickname(),
                authorities
        );
    }

    public UserDetailResponseDto getUserDetail(User user) {
        return new UserDetailResponseDto(user.getUsername(),
                user.getNickname(),
                user.getRole().toString());
    }
}
