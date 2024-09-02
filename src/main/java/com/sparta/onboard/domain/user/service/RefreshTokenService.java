package com.sparta.onboard.domain.user.service;

import com.sparta.onboard.domain.user.entity.RefreshToken;
import com.sparta.onboard.domain.user.repository.RefreshTokenRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
	
	private final RefreshTokenRepository refreshTokenRepository;
	
	public void save(String username, String refreshToken) {
		refreshTokenRepository.save(new RefreshToken(username, refreshToken));
	}
	
	public Optional<RefreshToken> findByRefreshToken(String refreshToken) {
		return refreshTokenRepository.findByRefreshToken(refreshToken);
	}
	
	public void deleteByRefreshToken(String refreshToken) {
		refreshTokenRepository.deleteByRefreshToken(refreshToken);
	}
}
