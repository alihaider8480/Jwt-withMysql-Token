package com.example.practice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.practice.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByEmail(String email);

}
