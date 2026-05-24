package com.beezu.beezu_api.security;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
	
	private final TokenConfig tokenConfig;
	
	public SecurityFilter(TokenConfig tokenConfig) {
		this.tokenConfig = tokenConfig;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizedHeader = request.getHeader("Authorization");
		if(Strings.isNotEmpty(authorizedHeader) && authorizedHeader.startsWith("Bearer ")) {
			String token = authorizedHeader.substring("Bearer ".length());
			Optional<JWTUserData> optUser = tokenConfig.validateToken(token);
			
			if(optUser.isPresent()) {
				
				JWTUserData userData = optUser.get();
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userData, null, List.of());
				SecurityContextHolder.getContext().setAuthentication(authentication);			
			}
			filterChain.doFilter(request, response);

		}
		else {
			filterChain.doFilter(request, response);
		}
		
	}

}
