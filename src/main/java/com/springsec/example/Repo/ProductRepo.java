package com.springsec.example.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springsec.example.Entity.User;

public interface ProductRepo extends JpaRepository<User,Integer> {

	Optional<User> findByName(String username);



}
