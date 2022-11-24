//package com.lmsapp.main.kafka;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.lmsapp.main.model.User;
//import com.lmsapp.main.payload.request.LoginRequest;
//import com.lmsapp.main.payload.response.JwtResponse;
//import com.lmsapp.main.security.jwt.JwtUtils;
//import com.lmsapp.main.service.UserDetailsImpl;
//import com.lmsapp.main.service.UserService;
//
//@RestController
//@RequestMapping("/api/v1.0/lms")
//public class KafkaController {
//
//	@Autowired
//	private KafkaTemplate<String, Object> kafkaTemplate;
//
//	private String kafkaTopic = "login_userdetails";
//
//	@Autowired
//	AuthenticationManager authenticationManager;
//
//	@Autowired
//	UserService userService;
//
//	@Autowired
//	PasswordEncoder encoder;
//
//	@Autowired
//	JwtUtils jwtUtils;
//
//	@PostMapping("/publishLoginDetails")
//	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//
//		if (userService.findByUsername(loginRequest.getUsername()).equals(false)) {
//			return new ResponseEntity<String>("Error: Invalid Username!", HttpStatus.BAD_REQUEST);
//		}
//
//		Optional<User> search = userService.findUser(loginRequest.getUsername());
//		if (!encoder.matches(loginRequest.getPassword(), search.get().getPassword())) {
//			return new ResponseEntity<String>("Error: Invalid Password!", HttpStatus.BAD_REQUEST);
//		}
//
//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		String jwt = jwtUtils.generateJwtToken(authentication);
//		System.out.println(authentication);
//		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
//				.collect(Collectors.toList());
//		JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
//				userDetails.getEmail(), roles);
//		kafkaTemplate.send(kafkaTopic, jwtResponse);
//		return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
//	}
//}
