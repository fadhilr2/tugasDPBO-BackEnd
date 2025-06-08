package com.dpbo.api.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dpbo.api.model.User;
import com.dpbo.api.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private final UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
		
		System.out.println("USER_DETAILS_SERVICE attempting to load user by phoneNumber " + phoneNumber);
		
		User user = userRepository.findByPhoneNumber(phoneNumber);
		
		if(user == null) {
			System.out.println("USER_DETAILS_SERVICE user not found with identifier " + phoneNumber);
			throw new UsernameNotFoundException("User not found with identifier " + phoneNumber);
		}
		System.out.println(user + "\nUSER_DETAILS_SERVICE user found. Mapping authorities");
		
		Set<GrantedAuthority> authorities = user.getRoles()
				.stream()
				.map((role) -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toSet());
		return new org.springframework.security.core.userdetails.User(user.getPhoneNumber(), user.getPassword(), authorities);
	}
	
}
