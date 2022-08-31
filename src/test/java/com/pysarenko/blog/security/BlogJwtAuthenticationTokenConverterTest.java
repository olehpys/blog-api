package com.pysarenko.blog.security;

import static org.assertj.core.api.Assertions.assertThat;

import com.pysarenko.blog.TestUtils;
import com.pysarenko.blog.security.model.UserProfile;
import com.pysarenko.blog.security.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

public class BlogJwtAuthenticationTokenConverterTest {

  private BlogJwtAuthenticationTokenConverter converter;

  @BeforeEach
  void setUp() {
    converter = new BlogJwtAuthenticationTokenConverter();
  }

  @Test
  void shouldConvertJwtToBlogJwtAuthenticationToken() {
    UserProfile expectedUserProfile = UserProfile.builder()
        .username(TestUtils.TEST_USERNAME)
        .role(UserRole.PUBLISHER)
        .build();

    var convert = converter.convert(Jwt.withTokenValue("1234567890")
        .header("kid", "qJDmoHLufpXmrFzu53NcVKJXM5BMpd2PLw84G+084ew=")
        .header("alg", "RS256")
        .claim("sub", "1234567890")
        .claim("cognito:username", TestUtils.TEST_USERNAME)
        .claim("custom:role", UserRole.PUBLISHER.name())
        .build());

    assertThat(convert).isNotNull();
    assertThat(convert.getUserProfile()).isEqualTo(expectedUserProfile);
  }
}
