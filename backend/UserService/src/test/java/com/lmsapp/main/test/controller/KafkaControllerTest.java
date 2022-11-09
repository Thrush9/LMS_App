package com.lmsapp.main.test.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lmsapp.main.kafka.KafkaController;
import com.lmsapp.main.model.ERole;
import com.lmsapp.main.model.Role;
import com.lmsapp.main.model.User;
import com.lmsapp.main.payload.request.LoginRequest;
import com.lmsapp.main.repository.RoleRepository;
import com.lmsapp.main.security.jwt.JwtUtils;
import com.lmsapp.main.service.UserDetailsImpl;
import com.lmsapp.main.service.UserServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("deprecation")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class KafkaControllerTest {

	@Autowired
	private MockMvc mockMvc;
	private User user;
	private Role role;
	
	@MockBean
	private UserServiceImpl userService;
	
	@MockBean
	AuthenticationManager authenticationManager;
	
	@MockBean
	UsernamePasswordAuthenticationToken UsernamePasswordAuthenticationToken;

	@MockBean
	RoleRepository roleRepository;

	@MockBean
	PasswordEncoder encoder;

	@MockBean
	JwtUtils jwtUtils;
	
	@InjectMocks
	private KafkaController kafkaController;
	
	@MockBean
	private KafkaTemplate<String, Object> kafkaTemplate;
	
	private  LoginRequest login;
	
	@BeforeEach
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(kafkaController).build();
		user = new User();
		user.setId(101L);
		user.setEmail("Test@gmail.com");
		user.setUsername("Test");
		user.setPassword("Test@123");
		
		Set<Role> roles = new HashSet<>();
		role = new Role();
		role.setId(1);
		role.setName(ERole.ROLE_USER);
		roles.add(role);
		
		user.setRoles(roles);

	    login = new LoginRequest();
	    login.setUsername("Test");
	    login.setPassword("Test@123");
	}
	
	@Test
	public void testPublishLoginDetailsSuccess() throws Exception {
		when(userService.findByUsername("Test")).thenReturn(true);
		Optional<User> optUser = Optional.of(user);
		when(userService.findUser(login.getUsername())).thenReturn(optUser);
		when(encoder.matches(login.getPassword(),optUser.get().getPassword())).thenReturn(true);
		
		Authentication authentication = mock(Authentication.class);
	    authentication.setAuthenticated(true);
	    when(authentication.isAuthenticated()).thenReturn(true);
	    when(authenticationManager.authenticate(any())).thenReturn(authentication);
	    UserDetailsImpl userDetails = UserDetailsImpl.build(user);
	    when(authentication.getPrincipal()).thenReturn(userDetails);

		mockMvc.perform( MockMvcRequestBuilders
			      .post("/api/v1.0/lms/publishLoginDetails")
			      .content(asJsonString(login))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testPublishLoginDetailsFailure1() throws Exception {
		when(userService.findByUsername("Test")).thenReturn(false);
		mockMvc.perform( MockMvcRequestBuilders
			      .post("/api/v1.0/lms/publishLoginDetails")
			      .content(asJsonString(login))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void testPublishLoginDetailsFailure2() throws Exception {
		when(userService.findByUsername("Test")).thenReturn(true);
		Optional<User> optUser = Optional.of(user);
		when(userService.findUser(login.getUsername())).thenReturn(optUser);
		when(encoder.matches(login.getPassword(),optUser.get().getPassword())).thenReturn(false);
		mockMvc.perform( MockMvcRequestBuilders
			      .post("/api/v1.0/lms/publishLoginDetails")
			      .content(asJsonString(login))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	private static String asJsonString(final Object obj) {
		try {
			ObjectMapper objmapper = new ObjectMapper();
			objmapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			objmapper.registerModule(new JavaTimeModule());
			return objmapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}