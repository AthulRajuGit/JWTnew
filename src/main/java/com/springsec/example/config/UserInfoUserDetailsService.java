package com.springsec.example.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.springsec.example.Entity.User;
import com.springsec.example.Repo.ProductRepo;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {
    
	@Autowired
	private ProductRepo productRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		  Optional<User> userName=  productRepo.findByName(username);
		  
		return userName.map(UserInfoUserDetails::new).orElseThrow(()->new UsernameNotFoundException("user not found"));
	}

}
