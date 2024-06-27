package com.mro.stockcontrol.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService {
	
	 @Bean
	    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
	        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	        manager.createUser(User.withUsername("user")
	          .password(bCryptPasswordEncoder.encode("12345"))
	          .roles("USER")
	          .build());
	        manager.createUser(User.withUsername("admin")
	          .password(bCryptPasswordEncoder.encode("12345"))
	          .roles("USER", "ADMIN")
	          .build());
	        return manager;
	    }

}
