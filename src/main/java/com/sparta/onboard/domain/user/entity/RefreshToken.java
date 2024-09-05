package com.sparta.onboard.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "refresh_token_user")
@NoArgsConstructor
public class RefreshToken {

    @Id
    private String username;

    @Indexed
    private String refreshToken;

    @TimeToLive
    @Value("${jwt.refresh-token.ttl}")
    private long ttl;

    public RefreshToken(String username, String refreshToken) {
        this.username = username;
        this.refreshToken = refreshToken;
    }

}
