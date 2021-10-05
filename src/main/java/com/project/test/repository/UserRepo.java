package com.project.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.test.model.User;

public interface UserRepo extends JpaRepository<User, Long>{

	User findByEmail(String email);
	
}
