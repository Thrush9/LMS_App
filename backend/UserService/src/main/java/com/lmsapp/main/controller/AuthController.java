package com.lmsapp.main.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lmsapp.main.model.ERole;
import com.lmsapp.main.model.Role;
import com.lmsapp.main.model.User;
import com.lmsapp.main.payload.request.LoginRequest;
import com.lmsapp.main.payload.request.SignupRequest;
import com.lmsapp.main.payload.response.JwtResponse;
import com.lmsapp.main.repository.RoleRepository;
import com.lmsapp.main.security.jwt.JwtUtils;
import com.lmsapp.main.service.UserDetailsImpl;
import com.lmsapp.main.service.UserService;

@CrossOrigin(origins = "http://iiht-lmsapp.s3-website.ap-south-1.amazonaws.com", maxAge = 3600)
@RestController
@RequestMapping("/api/v1.0/lms")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserService  userService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userService.findByUsername(signUpRequest.getUsername()).equals(true)) {
			return new ResponseEntity<String>("Error: Username is already taken!", HttpStatus.BAD_REQUEST);
		}

		if (userService.findByEmail(signUpRequest.getEmail()).equals(true)) {
			return new ResponseEntity<String>("Error: Email is already in use!", HttpStatus.BAD_REQUEST);
		}
		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userService.saveUser(user);
		return new ResponseEntity<String>("User registered successfully!", HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
	
		if (userService.findByUsername(loginRequest.getUsername()).equals(false)) {
			return new ResponseEntity<String>("Error: Invalid Username!", HttpStatus.BAD_REQUEST);
		}
		
		Optional<User> search = userService.findUser(loginRequest.getUsername());
		if(!encoder.matches(loginRequest.getPassword(),search.get().getPassword())){
			return new ResponseEntity<String>("Error: Invalid Password!", HttpStatus.BAD_REQUEST);
		}

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
        System.out.println(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return new ResponseEntity<>(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles),
				HttpStatus.OK);
	}
	
	@GetMapping("/fetchAllUsers")
	public ResponseEntity<?> fetchAllUsers() {
		try {	
		List<User> flightsList = userService.fetchAllUsers();
			return new ResponseEntity<List<User>>(flightsList, HttpStatus.OK);	
	} catch(Exception e) {
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
