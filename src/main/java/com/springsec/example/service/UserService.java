package com.springsec.example.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springsec.example.Entity.User;
import com.springsec.example.Repo.ProductRepo;
import com.springsec.example.dto.Products;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
     
	private final ProductRepo productRepo;
	
	private final PasswordEncoder encoder;

	List<Products> productList = null;

	@PostConstruct
	public void loadProductsFromDB() {
		productList = IntStream
				.rangeClosed(1, 100).mapToObj(i -> Products.builder().productId(i).name("product " + i)
						.qty(new Random().nextInt(10)).price(new Random().nextInt(5000)).build())
				.collect(Collectors.toList());
	}

	public String addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
		productRepo.save(user);
		return "user created";
	}

	public List<Products> getProducts() {
		return productList;
	}

	public  Products getById(int id) {
	     return productList.stream().filter(p->p.getProductId()==id)
		.findAny()
		.orElseThrow(()-> new RuntimeException("product"+id+"not found"));		
	}

}
