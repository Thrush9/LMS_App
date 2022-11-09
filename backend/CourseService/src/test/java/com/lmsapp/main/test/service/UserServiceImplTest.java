package com.lmsapp.main.test.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.lmsapp.main.model.ERole;
import com.lmsapp.main.model.Role;
import com.lmsapp.main.model.User;
import com.lmsapp.main.repository.UserRepository;
import com.lmsapp.main.service.UserService;
import com.lmsapp.main.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
	
	@Mock
	private UserRepository userRepo;
	
	private UserService userService;

	private User user;
	
	private Role role;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@BeforeEach
	public void setUp() throws Exception {
		userService = new UserServiceImpl(userRepo);
		
		user = new User("Test","Test@gmail.com","Test@123");
		user.setId(101L);
		
		Set<Role> roles = new HashSet<>();
		role = new Role(ERole.ROLE_USER);
		role.setId(1);
		roles.add(role);
		
		user.setRoles(roles);
	}
	
	@Test
	public void testLoadUserByUsernameSuccess()  {
		lenient().when(userRepo.save(user)).thenReturn(user);
		Optional<User> opUser = Optional.of(user);
		when(userRepo.findUserByUsername(user.getUsername())).thenReturn(opUser);
		UserDetails result = userServiceImpl.loadUserByUsername("Test");
		assertNotNull(result);
	}
	
	@Test
	public void testLoadUserByUsernameFailure()  {
		lenient().when(userRepo.save(user)).thenReturn(user);
		UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.loadUserByUsername("Test123"));
		 assertEquals("User Not Found with username: Test123", exception.getMessage());
	}

	@Test
	public void testSaveUserSuccess()  {
		lenient().when(userRepo.findById(101L)).thenReturn(Optional.empty());
		when(userRepo.save(user)).thenReturn(user);
		User status = userService.saveUser(user);
		assertEquals(user, status);
	}
	
	@Test
	public void testFindByUsernameSuccess()  {
		lenient().when(userRepo.save(user)).thenReturn(user);
		when(userRepo.existsByUsername(user.getUsername())).thenReturn(true);
		Boolean status = userService.findByUsername("Test");
		assertEquals(status, true);
	}
	
	@Test
	public void testfindByEmailSuccess()  {
		lenient().when(userRepo.save(user)).thenReturn(user);
		when(userRepo.existsByEmail(user.getEmail())).thenReturn(true);
		Boolean status = userService.findByEmail("Test@gmail.com");
		assertEquals(status, true);
	}
	
	@Test
	public void testFindUserSuccess()  {
		Optional<User> optUser = Optional.of(user);
		lenient().when(userRepo.save(user)).thenReturn(user);
	    when(userRepo.findUserByUsername(user.getUsername())).thenReturn(optUser);
		Optional<User> status = userService.findUser("Test");
		assertEquals(optUser, status);
	}

    @Test
    public void testFetchAllUsersSucess() {
    	List<User> usersList = new ArrayList<>();
    	usersList.add(user);
    	lenient().when(userRepo.save(user)).thenReturn(user);
		when(userRepo.findAll()).thenReturn(usersList);
		List<User> users = userService.fetchAllUsers();
		users.forEach(user -> System.out.println(user.toString()));
		assertEquals(usersList, users);
    }

}