package com.lmsapp.main.test.repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lmsapp.main.model.ERole;
import com.lmsapp.main.model.Role;
import com.lmsapp.main.model.User;
import com.lmsapp.main.repository.UserRepository;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepo;

	User user;
	
	Role role;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
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
	}

	@AfterEach
	public void tearDown() throws Exception {
		userRepo.deleteAll();
	}

	@Test
	public void testAddUserSuccess() {
		userRepo.save(user);
		Boolean userExists = userRepo.existsByUsername("Test");
		assertThat(userExists, is(true));
	}
	
	@Test
	public void testAddUserFailure() {
		userRepo.save(user);
		Optional<User> userExists = userRepo.findById(100L);
		assertThat(userExists.isPresent(), is(false));
	}
	
	@Test
	public void testExistByUsernameSuccess() {
		userRepo.save(user);
		Boolean userExists = userRepo.existsByUsername("Test");
			assertThat(userExists, is(true));
	}
	
	
	@Test
	public void testExistByEmailSuccess() {
		userRepo.save(user);
		Boolean userExists = userRepo.existsByEmail("Test@gmail.com");
		assertThat(userExists, is(true));
	}



}
