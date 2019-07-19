package com.sshenv.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sshenv.spring.service.IAccountService;

class AccountServiceTest {

	@Test
	void testSaveAccount() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
		IAccountService accountService = ac.getBean("accountService", IAccountService.class);
	}

}
