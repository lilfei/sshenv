package com.sshenv.spring.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Component;

import com.sshenv.hibernate.Student;
import com.sshenv.spring.dao.IStudentDao;
import com.sshenv.spring.service.IStudentService;

@Component(value="studentService")
public class StudentServiceImpl implements IStudentService {

	private IStudentDao studenttDao;
	
	@Override
	public void saveStudent(Student stu) {
		studenttDao.save(stu);
	}

	@Override
	public void updateStudent(Student stu) {
		studenttDao.update(stu);
	}

	@Override
	public void deleteStudent(Student stu) {
		studenttDao.delete(stu);
	}

	@Override
	public Student findStudentById(Integer stuId) {
		return studenttDao.findById(stuId);
	}

	@Override
	public List<Student> findAllStudent(DetachedCriteria dCrideria) {
		return studenttDao.findAll(dCrideria);
	}

}
