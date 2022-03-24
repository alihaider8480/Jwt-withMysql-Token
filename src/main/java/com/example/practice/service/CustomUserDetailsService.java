package com.example.practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.practice.entity.User;
import com.example.practice.repo.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	 @Autowired
	    PasswordEncoder passwordEncoder;

	    @Autowired
	    UserRepository userRepository;
	    
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    	 User user=userRepository.findByEmail(email);
	         if(user!=null) {
	             //this will return user from database
	             return org.springframework.security.core.userdetails.User.withUsername(user.getEmail()).password(user.getPassword())
	                     .disabled(false).authorities(user.getRole()).build();
	         }
	         else{
	             throw new UsernameNotFoundException("Invalid Credentials");
	         }
	}

}
