package com.sparta.onboard.common.security;

import com.sparta.onboard.common.exception.CustomException;
import com.sparta.onboard.domain.user.entity.RefreshToken;
import com.sparta.onboard.domain.user.repository.RefreshTokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Optional;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenProviderTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private TokenProvider tokenProvider;

    private static final String username = "testUser";

    @BeforeEach
    void setUp() {
        tokenProvider = new TokenProvider(refreshTokenRepository);
        ReflectionTestUtils.setField(tokenProvider, "secretKey", "8J2FvjNhZHvXuKH0+IQoDssXswVWfYDbJfhsBl+w7Ik=");
        ReflectionTestUtils.setField(tokenProvider, "accessTokenTtl", 3600000);
        tokenProvider.init();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("액세스 토큰 생성 테스트")
    void testCreateAccessToken() {
        String accessToken = tokenProvider.createAccessToken(username);

        assertNotNull(accessToken);
        assertTrue(accessToken.startsWith(TokenProvider.JWT_PREFIX));
    }

    @Test
    @DisplayName("액세스 토큰 검증 테스트 - 성공")
    void testValidateAccessToken_ValidToken() {
        String accessToken = tokenProvider.createAccessToken(username);

        boolean isValid = tokenProvider.validateAccessToken(accessToken.substring(7)); // Bearer 부분 제거
        assertTrue(isValid);
    }

    @Test
    @DisplayName("액세스 토큰 검증 테스트 - 실패")
    void testValidateAccessToken_InvalidToken() {
        String invalidToken = "Bearer invalidToken";

        boolean isValid = tokenProvider.validateAccessToken(invalidToken);
        assertFalse(isValid);
    }

    @Test
    @DisplayName("액세스 토큰에서 사용자 이름 추출 테스트")
    void testGetUsernameFromAccessToken() {
        String accessToken = tokenProvider.createAccessToken(username);

        String extractedUsername = tokenProvider.getUsernameFromAccessToken(accessToken.substring(7));
        assertEquals(username, extractedUsername);
    }
}