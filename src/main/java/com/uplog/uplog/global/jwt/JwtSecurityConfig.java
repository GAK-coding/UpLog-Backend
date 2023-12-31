package com.uplog.uplog.global.jwt;

import com.uplog.uplog.domain.member.dao.RedisDao;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private TokenProvider tokenProvider;
    private final RedisTemplate redisTemplate;
    public JwtSecurityConfig(TokenProvider tokenProvider, RedisTemplate redisTemplate) {

        this.tokenProvider = tokenProvider;
        this.redisTemplate=redisTemplate;
    }

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(
                new JwtFilter(tokenProvider,redisTemplate),
                UsernamePasswordAuthenticationFilter.class
        );
    }
}
