package com.pysarenko.blog.security;

import com.pysarenko.blog.security.model.UserProfile;
import lombok.Getter;
import org.springframework.security.core.Transient;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Transient
public class BlogJwtAuthenticationToken extends JwtAuthenticationToken implements BlogAuthentication {

  @Getter
  private final transient UserProfile userProfile;

  public BlogJwtAuthenticationToken(Jwt jwt, UserProfile userProfile) {
    super(jwt);
    this.userProfile = userProfile;
    super.setAuthenticated(true);
  }
}
