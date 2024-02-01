package com.rm;

import com.rm.user.UserController;
import com.rm.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RmApplicationTests {
	@Autowired
	UserService userService;
	@Test
	void contextLoads() {

	}

}
