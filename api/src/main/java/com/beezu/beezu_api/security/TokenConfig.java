package com.beezu.beezu_api.security;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.beezu.beezu_api.models.User;

@Component
public class TokenConfig {
	
	@Value("${api.security.token.secret}")
	private String secret;
	
	public String generateToken(User user) {
		
		Algorithm algorithm = Algorithm.HMAC256(secret);
		return JWT.create().withClaim("userId", user.getId())
				.withSubject(user.getEmail())
				.withExpiresAt(Instant.now().plusSeconds(86400))
				.withIssuedAt(Instant.now())
				.sign(algorithm);
	}

	public Optional<JWTUserData> validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			DecodedJWT decode =  JWT.require(algorithm).build().verify(token);
			JWTUserData data = new JWTUserData(decode.getClaim("userId").asLong(), decode.getSubject());
			
			return Optional.of(data);
					
		}
		catch(JWTVerificationException ex) {
			return Optional.empty();
		}
	}
	
	

}
