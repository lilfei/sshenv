package com.sshenv.spring.ui;

import java.util.EnumSet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sshenv.hibernate.Teacher;
import com.sshenv.spring.service.IAccountService;

public class Client {

	@Autowired
	public static IAccountService accountService;
	
	public static void main(String[] args) {
		Teacher t = new Teacher();
		t.setName("teacherName1");
		if(accountService == null) {
			System.out.println("null");
			return;
		}
		accountService.saveAccount(t);
	}
	
	@Test
	public void createTable() {
		ServiceRegistry sr = new StandardServiceRegistryBuilder().configure().build();
		
		Metadata metadata = new MetadataSources(sr).buildMetadata();
		EnumSet<TargetType> targetTypes = EnumSet.of(TargetType.DATABASE);
		
		SchemaExport se = new SchemaExport();
		se.create(targetTypes, metadata);
	}
	
	@Test
	public void add() {
		
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		
		SessionFactory sessionFactory = null;
		Session session = null;
		
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
		
		if(sessionFactory != null) {
			session = sessionFactory.openSession();
			session.beginTransaction();
			
			Teacher stu1 = new Teacher();
			stu1.setName("llf_ssh_1");
			stu1.setAge(1888);
			
			session.save(stu1);
			session.getTransaction().commit();
			session.close();
		}
	}
	
}
