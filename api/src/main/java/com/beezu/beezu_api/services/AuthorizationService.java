package com.beezu.beezu_api.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.beezu.beezu_api.repositories.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	public AuthorizationService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
	}

}
