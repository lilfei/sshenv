package com.sshenv.spring.service.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sshenv.spring.dao.IAcountDao;
import com.sshenv.spring.service.IAccountService;

@Component(value="accountService")
public class AccountServiceImpl implements IAccountService {

//	@Autowired
//	@Qualifier(value="accountDao1")
	@Resource(name="accountDao")
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
	public void saveAccount() {
		// TODO Auto-generated method stub
		System.out.println("haha: " + driver);
		accountDao.saveAccount();
	}

}
