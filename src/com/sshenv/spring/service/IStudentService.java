package com.sshenv.spring.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.sshenv.hibernate.Student;

public interface IStudentService {

	void saveStudent(Student stu);

	void updateStudent(Student stu);

	void deleteStudent(Student stu);

	Student findStudentById(Integer stuId);

	List<Student> findAllStudent(DetachedCriteria dCrideria);
	
}
