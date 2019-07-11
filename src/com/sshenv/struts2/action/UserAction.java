package com.sshenv.struts2.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3792189870650627813L;
	private String userName;
	private String passWord;

	/**
	 * http://localhost:8080/StrutsTest1/login?userName=haha&&passWord=123456
	 * @return
	 * @throws IOException
	 */
	public String login() throws IOException {
		System.out.println(userName + " , " + passWord);

		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter writer = response.getWriter();
		writer.write("login success ! " + userName);
		writer.flush();

		return null;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

}
