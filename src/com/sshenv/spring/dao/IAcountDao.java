package com.sshenv.spring.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.sshenv.hibernate.Teacher;

public interface IAcountDao {

	void save(Teacher teacher);

	void update(Teacher teacher);

	void delete(Teacher teacher);

	Teacher findById(Integer teacherId);

	List<Teacher> findAll(DetachedCriteria dCrideria);
	
}
