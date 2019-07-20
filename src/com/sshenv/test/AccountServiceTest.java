package com.sshenv.test;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sshenv.hibernate.Teacher;
import com.sshenv.spring.service.IAccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "bean.xml" })
class AccountServiceTest {

	@Autowired
	private IAccountService cs;

	@Test
	void testSaveAccount() {

	}

	@Test
	public void testFindAllTeacher() {
		DetachedCriteria dCrideria = DetachedCriteria.forClass(Teacher.class);
		List list = cs.findAllTeacher(dCrideria);
		for (Object o : list) {
			System.out.println(o);
		}
	}
}
