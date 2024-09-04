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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenProviderTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private TokenProvider tokenProvider;

    private static final String USERNAME = "testUser";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenProvider = new TokenProvider(refreshTokenRepository);
        ReflectionTestUtils.setField(tokenProvider, "secretKey", "8J2FvjNhZHvXuKH0+IQoDssXswVWfYDbJfhsBl+w7Ik=");
        ReflectionTestUtils.setField(tokenProvider, "accessTokenTtl", 3600000);
        tokenProvider.init();
    }

    @Test
    @DisplayName("액세스 토큰 생성 테스트")
    void testCreateAccessToken() {
        String accessToken = tokenProvider.createAccessToken(USERNAME);

        assertNotNull(accessToken);
        assertTrue(accessToken.startsWith(TokenProvider.JWT_PREFIX));
    }

    @Test
    @DisplayName("액세스 토큰 검증 테스트 - 성공")
    void testValidateAccessToken_ValidToken() {
        String accessToken = tokenProvider.createAccessToken(USERNAME);

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
        String accessToken = tokenProvider.createAccessToken(USERNAME);

        String extractedUsername = tokenProvider.getUsernameFromAccessToken(accessToken.substring(7));
        assertEquals(USERNAME, extractedUsername);
    }

    @Test
    @DisplayName("쿠키에서 리프레시 토큰 추출 테스트")
    void testGetRefreshToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Cookie[] cookies = { new Cookie(TokenProvider.REFRESH_TOKEN_COOKIE, "testRefreshToken") };

        when(request.getCookies()).thenReturn(cookies);

        String refreshToken = tokenProvider.getRefreshToken(request);
        assertEquals("testRefreshToken", refreshToken);
    }

    @Test
    @DisplayName("리프레시 토큰 검증 테스트 - 성공")
    void testValidateRefreshToken_ValidToken() {
        String refreshTokenValue = "validRefreshToken";
        RefreshToken refreshToken = new RefreshToken(USERNAME, refreshTokenValue);

        when(refreshTokenRepository.findByRefreshToken(refreshTokenValue)).thenReturn(Optional.of(refreshToken));

        boolean isValid = tokenProvider.validateRefreshToken(refreshTokenValue);
        assertTrue(isValid);
    }

    @Test
    @DisplayName("리프레시 토큰 검증 테스트 - 실패")
    void testValidateRefreshToken_InvalidToken() {
        String invalidToken = "invalidRefreshToken";

        when(refreshTokenRepository.findByRefreshToken(invalidToken)).thenReturn(Optional.empty());

        boolean isValid = tokenProvider.validateRefreshToken(invalidToken);
        assertFalse(isValid);
    }

    @Test
    @DisplayName("유효한 리프레시 토큰에서 사용자 이름 추출 테스트")
    void testGetUsernameFromRefreshToken_ValidToken() {
        String refreshTokenValue = "validRefreshToken";
        RefreshToken refreshToken = new RefreshToken(USERNAME, refreshTokenValue);

        when(refreshTokenRepository.findByRefreshToken(refreshTokenValue)).thenReturn(Optional.of(refreshToken));

        String givenUsername = tokenProvider.getUsernameFromRefreshToken(refreshTokenValue);
        assertEquals(USERNAME, givenUsername);
    }

    @Test
    @DisplayName("잘못된 리프레시 토큰에서 사용자 이름 추출 실패 테스트")
    void testGetUsernameFromRefreshToken_InvalidToken() {
        String invalidToken = "invalidRefreshToken";

        when(refreshTokenRepository.findByRefreshToken(invalidToken)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> {
            tokenProvider.getUsernameFromRefreshToken(invalidToken);
        });
    }
}