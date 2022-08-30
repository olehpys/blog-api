package com.pysarenko.blog.config;

import com.pysarenko.blog.security.BlogJwtAuthenticationTokenConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // To configure method-level security
@RequiredArgsConstructor
public class SecurityConfig {

  private final BlogJwtAuthenticationTokenConverter blogJwtAuthenticationTokenConverter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
        .authorizeRequests(expressionInterceptUrlRegistry -> expressionInterceptUrlRegistry
            .anyRequest()
            .authenticated())
        .oauth2ResourceServer()
        .jwt()
        .jwtAuthenticationConverter(blogJwtAuthenticationTokenConverter);
    return http.build();
  }
}
