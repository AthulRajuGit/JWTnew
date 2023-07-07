package com.springsec.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	//authentication
	 public UserDetailsService userDetailsService() {
		
		//UserDetails admin=User.withUsername("athul").password(encoder.encode("psw1")).roles("ADMIN").build();
		//UserDetails user=User.withUsername("Joseph").password(encoder.encode("psw2")).roles("USER").build();
	//  return new InMemoryUserDetailsManager(admin,user);
		
		return new UserInfoUserDetailsService();
	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity sec) throws Exception{
	return	sec.csrf().disable().authorizeHttpRequests().requestMatchers("/products/welcome","/products/new").permitAll()
			.and().authorizeHttpRequests()
		    .requestMatchers("/products/**")
		    .authenticated().and().formLogin().and().build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authentication =new DaoAuthenticationProvider();
		authentication.setPasswordEncoder(passwordEncoder());
		authentication.setUserDetailsService(userDetailsService());
		return authentication;
	}
}
