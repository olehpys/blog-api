package com.pysarenko.blog.security;

import com.pysarenko.blog.security.model.UserProfile;
import com.pysarenko.blog.security.model.UserRole;
import java.util.Optional;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class BlogJwtAuthenticationTokenConverter implements Converter<Jwt, BlogJwtAuthenticationToken> {

  @Override
  public BlogJwtAuthenticationToken convert(Jwt source) {
    UserProfile userProfile = UserProfile.builder()
        .username(source.getClaim("cognito:username"))
        .role(UserRole.valueOf((String) Optional.ofNullable(source.getClaim("custom:role"))
            .orElse(UserRole.PUBLISHER.name())))
        .build();

    return new BlogJwtAuthenticationToken(source, userProfile);
  }
}
