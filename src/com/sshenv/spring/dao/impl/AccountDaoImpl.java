package com.sshenv.spring.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.sshenv.hibernate.Teacher;
import com.sshenv.spring.dao.IAcountDao;


@Repository("accountDao")
public class AccountDaoImpl implements IAcountDao {

	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public void save(Teacher teacher) {
		hibernateTemplate.save(teacher);
	}

	@Override
	public void update(Teacher teacher) {
		hibernateTemplate.update(teacher);
	}

	@Override
	public void delete(Teacher teacher) {
		hibernateTemplate.delete(teacher);
	}

	@Override
	public Teacher findById(Integer teacherId) {
		return null;
	}

	@Override
	public List<Teacher> findAll(DetachedCriteria dCrideria) {
		return (List<Teacher>) hibernateTemplate.findByCriteria(dCrideria);
	}	
	
}
