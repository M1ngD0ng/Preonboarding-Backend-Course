package com.sparta.onboard.domain.user.repository;

import com.sparta.onboard.domain.user.entity.RefreshToken;
import java.util.Optional;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;

@Repository
public class RefreshTokenRepository {
    private final RMap<String, RefreshToken> refreshTokenMap;

    public RefreshTokenRepository(RedissonClient redissonClient) {
        this.refreshTokenMap = redissonClient.getMap("refreshToken");
    }

    public void save(RefreshToken refreshToken) {
        refreshTokenMap.put(refreshToken.getRefreshToken(), refreshToken);
    }

    public Optional<RefreshToken> findByRefreshToken(String refreshToken) {
        return Optional.ofNullable(refreshTokenMap.get(refreshToken));
    }

    public void deleteByRefreshToken(String refreshToken) {
        refreshTokenMap.remove(refreshToken);
    }

}
