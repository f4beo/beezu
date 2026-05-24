package com.beezu.beezu_api.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticatedUserUtil {

	public static Long getAuthenticatedUserId() {

	    Authentication authentication =
	            SecurityContextHolder.getContext().getAuthentication();

	    if(authentication == null ||
	       !(authentication.getPrincipal() instanceof JWTUserData userData)) {

	        throw new RuntimeException("User not authenticated");
	    }

	    return userData.userId();
	}
}
