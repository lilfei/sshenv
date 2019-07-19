package com.sshenv.spring.service;

import org.hibernate.criterion.DetachedCriteria;
import java.util.List;

import com.sshenv.hibernate.Teacher;

public interface IAccountService {
	
	void saveAccount(Teacher teacher);

	void updateAccount(Teacher teacher);

	void deleteAccount(Teacher teacher);

	Teacher findTeacherById(Integer teacherId);

	List<Teacher> findAllTeacher(DetachedCriteria dCrideria);
	
}
