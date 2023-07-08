package com.springsec.example.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springsec.example.config.UserInfoUserDetailsService;
import com.springsec.example.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;
	
	
	private final UserInfoUserDetailsService userDetailService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		   // TODO Auto-generated method stub
		   String AuthHeader=request.getHeader("Authorization");
		   String Token=null;
		   String username=null;
		   if(AuthHeader!=null &&AuthHeader.startsWith("Bearer ")) {
			  Token = AuthHeader.substring(7);
			  username=jwtService.extractUserName(Token);		  
		   }
		   if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			   UserDetails userDetail=userDetailService.loadUserByUsername(username);
			   if(jwtService.validateToken(Token,userDetail)) {
				   UsernamePasswordAuthenticationToken authToken =new UsernamePasswordAuthenticationToken(userDetail,null,userDetail.getAuthorities());
				   authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				   SecurityContextHolder.getContext().setAuthentication(authToken);
				   
			   }
		   }
		   filterChain.doFilter(request, response);
	}

	
	
}
