package com.lmsapp.main.test.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.lmsapp.main.model.ERole;
import com.lmsapp.main.model.Role;
import com.lmsapp.main.model.User;
import com.lmsapp.main.repository.UserRepository;
import com.lmsapp.main.service.UserServiceImpl;


public class UserServiceImplTest {
	@Mock
	UserRepository userRepo;

	User user;
	
	Role role;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		user = new User("Test","Test@gmail.com","Test@123");
		user.setId(101L);
		
		Set<Role> roles = new HashSet<>();
		role = new Role(ERole.ROLE_USER);
		role.setId(1);
		roles.add(role);
		
		user.setRoles(roles);
	}
	
	@Test
	public void loadUserByUsernameSuccessTest()  {
		Mockito.when(userRepo.save(user)).thenReturn(user);
		Optional<User> opUser = Optional.of(user);
		Mockito.when(userRepo.findByUsername(user.getUsername())).thenReturn(opUser);
		UserDetails result = userServiceImpl.loadUserByUsername("Test");
		assertNotNull(result);
	}
	
	@Test
	public void loadUserByUsernameFailureTest()  {
		Mockito.when(userRepo.save(user)).thenReturn(user);
		UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.loadUserByUsername("Test123"));
		 assertEquals("User Not Found with username: Test123", exception.getMessage());
	}

	@Test
	public void saveUserSuccessTest()  {
		when(userRepo.findById(101L)).thenReturn(Optional.empty());
		when(userRepo.save(Mockito.any(User.class))).thenReturn(new User());
		Mockito.when(userRepo.save(user)).thenReturn(user);
		User status = userServiceImpl.saveUser(user);
		assertThat(user, is(status));

	}
	
	@Test
	public void findByUsernameSuccessTest()  {
		Mockito.when(userRepo.save(user)).thenReturn(user);
		Mockito.when(userRepo.existsByUsername(user.getUsername())).thenReturn(true);
		Boolean status = userServiceImpl.findByUsername("Test");
		assertThat(status, is(true));
	}
	
	@Test
	public void findByEmailSuccessTest()  {
		Mockito.when(userRepo.save(user)).thenReturn(user);
		Mockito.when(userRepo.existsByEmail(user.getEmail())).thenReturn(true);
		Boolean status = userServiceImpl.findByEmail("Test@gmail.com");
		assertThat(status, is(true));
	}
	
	@Test
	public void findUserSuccessTest()  {
		Optional<User> optUser = Optional.of(user);
		Mockito.when(userRepo.save(user)).thenReturn(user);
	    Mockito.when(userRepo.findByUsername(user.getUsername())).thenReturn(optUser);
		Optional<User> status = userServiceImpl.findUser("Test");
		assertThat(optUser, is(status));
	}

    @Test
    public void fetchAllUsersSucessTest() {
    	List<User> usersList = new ArrayList<>();
    	usersList.add(user);
    	Mockito.when(userRepo.save(user)).thenReturn(user);
		Mockito.when(userRepo.findAll()).thenReturn(usersList);
		List<User> users = userServiceImpl.fetchAllUsers();
		users.forEach(user -> System.out.println(user.toString()));
		assertThat(usersList, is(users));
    }

}