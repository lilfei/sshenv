package com.sshenv.spring.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sshenv.hibernate.Teacher;
import com.sshenv.spring.dao.IAcountDao;
import com.sshenv.spring.service.IAccountService;

@Component(value="accountService")
public class AccountServiceImpl implements IAccountService {

	@Autowired
	private IAcountDao accountDao;

	@Value("${jdbc.driver}")
	private String driver;
	
	@PostConstruct
	public void init() {
		System.out.println("对象初始化了");
	}
	
	@PreDestroy
	public void destroy() {
		System.out.println("对象销毁了");
	}

	@Override
	public void saveAccount(Teacher teacher) {
		accountDao.save(teacher);
	}

	@Override
	public void updateAccount(Teacher teacher) {
		accountDao.update(teacher);
	}

	@Override
	public void deleteAccount(Teacher teacher) {
		accountDao.delete(teacher);
	}

	@Override
	public Teacher findTeacherById(Integer teacherId) {
		return accountDao.findById(teacherId);
	}

	@Override
	public List<Teacher> findAllTeacher(DetachedCriteria crideria) {
		return accountDao.findAll(crideria);
	}

}
