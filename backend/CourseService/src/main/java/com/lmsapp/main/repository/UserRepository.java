package com.lmsapp.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lmsapp.main.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findUserByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
