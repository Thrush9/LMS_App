package com.lmsapp.main;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CourseServiceApplicationTests {

//	@Test
//	void contextLoads() {
//	}
	
	@Test
	public void testMain() {
		CourseServiceApplication.main(new String[] {});
		assertTrue(true);
	}

}
