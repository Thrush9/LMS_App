package com.lmsapp.main;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ZuulGatewayApplicationTests {

//	@Test
//	void contextLoads() {
//	}

	@Test
	public void testMain() {
		ZuulGatewayApplication.main(new String[] {});
		assertTrue(true);
	}
}
