package com.sshenv.spring.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.sshenv.hibernate.Student;

public interface IStudentDao {

	void save(Student stu);

	void update(Student stu);

	void delete(Student stu);

	Student findById(Integer stu);

	List<Student> findAll(DetachedCriteria dCrideria);
	
}
