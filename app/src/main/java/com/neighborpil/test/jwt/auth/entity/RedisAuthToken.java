package com.neighborpil.test.jwt.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash("token")
public class RedisAuthToken {

    @Id
    private String uuid;
    private String token;
}
