package com.pysarenko.blog.security.utils;

import com.pysarenko.blog.security.BlogAuthentication;
import com.pysarenko.blog.security.model.UserProfile;
import com.pysarenko.blog.security.model.UserRole;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class BlogSecurityUtils {

  public static UserProfile getUserProfileFromSecurityContext() {
    var authentication = (BlogAuthentication) SecurityContextHolder.getContext().getAuthentication();

    return authentication.getUserProfile();
  }

  public static String getUsernameFromSecurityContext() {
    var userProfile = getUserProfileFromSecurityContext();

    return userProfile.getUsername();
  }

  public static UserRole getUserRoleFromSecurityContext() {
    var userProfile = getUserProfileFromSecurityContext();

    return userProfile.getRole();
  }

  public static boolean isAdmin() {
    return UserRole.ADMIN.equals(getUserProfileFromSecurityContext().getRole());
  }
}
