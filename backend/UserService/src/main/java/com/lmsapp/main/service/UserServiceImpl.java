package com.lmsapp.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmsapp.main.model.User;
import com.lmsapp.main.repository.UserRepository;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		return UserDetailsImpl.build(user);
	}

	@Override
	public Boolean findByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public Boolean findByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public Optional<User> findUser(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> fetchAllUsers() {
		return userRepository.findAll();
	}

}
