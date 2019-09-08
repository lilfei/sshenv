package com.sshenv.spring.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.sshenv.hibernate.Student;
import com.sshenv.spring.dao.IStudentDao;

@Component(value="studentDao")
public class StudentDaoImpl implements IStudentDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public void save(Student stu) {
		hibernateTemplate.save(stu);
	}

	@Override
	public void update(Student stu) {
		hibernateTemplate.update(stu);
	}

	@Override
	public void delete(Student stu) {
		hibernateTemplate.delete(stu);
	}

	@Override
	public Student findById(Integer stu) {
		return null;
	}

	@Override
	public List<Student> findAll(DetachedCriteria dCrideria) {
		return (List<Student>) hibernateTemplate.findByCriteria(dCrideria);
	}

}
