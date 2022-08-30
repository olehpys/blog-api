package com.pysarenko.blog.security;

import com.pysarenko.blog.security.model.UserProfile;
import org.springframework.security.core.Authentication;

public interface BlogAuthentication extends Authentication {

  UserProfile getUserProfile();
}
