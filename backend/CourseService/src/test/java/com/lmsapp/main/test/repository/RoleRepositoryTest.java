package com.lmsapp.main.test.repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.lmsapp.main.model.ERole;
import com.lmsapp.main.model.Role;
import com.lmsapp.main.model.User;
import com.lmsapp.main.repository.RoleRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleRepositoryTest {

	@Autowired
	RoleRepository roleRepo;

	User user;

	Role role;

	@BeforeEach
	public void setUp() {
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
		roleRepo.deleteAll();
	}

	@Test
	public void testFindRoleByNameSuccess() {
		roleRepo.save(role);
		Optional<Role> roleOpt = roleRepo.findRoleByName(ERole.ROLE_USER);
		Role roleObj = roleOpt.get();
		assertEquals(roleObj.getName(),role.getName());
	}

}
