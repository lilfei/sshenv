package com.sshenv.struts2.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class PostStringAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8525984168693751782L;
	private String userName;
	private int mobileNumber;

	public String postString() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		ServletInputStream is = request.getInputStream();

		StringBuilder sb = new StringBuilder();

		int len = 0;
		byte[] buf = new byte[1024];

		while ((len = is.read(buf)) != -1) {
			sb.append(new String(buf, 0, len));
		}

		System.out.println("PostStringAction: " + sb.toString());

//		HttpServletResponse response = ServletActionContext.getResponse();
//		PrintWriter writer = response.getWriter();
//		writer.write("PostStringAction: login success ! ");
//		writer.flush();
		
		return SUCCESS;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(int mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
}
