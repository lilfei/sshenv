package com.sshenv.test;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sshenv.hibernate.Student;
import com.sshenv.hibernate.Teacher;
import com.sshenv.spring.service.IAccountService;
import com.sshenv.spring.service.IStudentService;

/**
 * junit4使用@RunWith(SpringJUnit4ClassRunner.class)
 * junit5使用@ExtendWith(SpringExtension.class)
 * 
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class AccountServiceTest {

	@Autowired
	private IAccountService accountService;

	@Autowired
	private IStudentService studentService;

	@Test
	public void testSaveAccount() {

	}

	@Test
	public void testFindAllTeacher() {
		DetachedCriteria dCrideria = DetachedCriteria.forClass(Teacher.class);
		if (accountService == null) {
			System.out.println("cs is null");
			return;
		}
		List<Teacher> list = accountService.findAllTeacher(dCrideria);
		for (Object o : list) {
			System.out.println(o);
		}
	}

	@Test
	public void testFindAllStudent() {
		DetachedCriteria dCrideria = DetachedCriteria.forClass(Student.class);
		if (studentService == null) {
			System.out.println("cs is null");
			return;
		}
		List<Student> list = studentService.findAllStudent(dCrideria);
		for (Object o : list) {
			System.out.println(o);
		}
	}
}
