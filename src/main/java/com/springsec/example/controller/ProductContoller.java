package com.springsec.example.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springsec.example.Entity.User;
import com.springsec.example.dto.Products;
import com.springsec.example.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductContoller {
	
	private final UserService userService;
	

	@GetMapping("/welcome")
	public String welcome() {
		return "this endpoint is not welcome";
	}
	
	@PostMapping("/new")
	@ResponseStatus(HttpStatus.CREATED)
	public String saveUser(@RequestBody User user) {
		return userService.addUser(user);
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<Products> getAllProducts(){
		return userService.getProducts();
		
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@ResponseStatus(HttpStatus.FOUND)
	public Products getProductById(@PathVariable int id) {
		return userService.getById(id);
		
	}
	
}
