package com.sshenv.gsww;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class getSignAction extends ActionSupport{

	private String url;
	private String appId;
	private String appKey;
	private String curTime;

	public String getSign() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		System.out.println("url: " + url);
		System.out.println("appId: " + appId);
		System.out.println("appKey: " + appKey);

		String sign = "";
		try {
			sign = ServiceInvocation.gatewaySignEncode(getAppId(), getAppKey(), getCurTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

		PrintWriter writer = response.getWriter();
		writer.write(sign);
		writer.flush();
		
		System.out.println("sign: " + sign);

		return null;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getCurTime() {
		return curTime;
	}

	public void setCurTime(String curTime) {
		this.curTime = curTime;
	}

}
