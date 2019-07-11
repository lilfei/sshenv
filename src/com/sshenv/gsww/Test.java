package com.sshenv.gsww;

import com.alibaba.fastjson.JSONObject;

import okhttp3.*;

public class Test {
	public static void main(String[] args) {
		String appId = "7084c19d23954d7a8ce1a11fc436afb1";
		String appKey = "NzA4NGMxOWQyMzk1NGQ3YThjZTFhMTFmYzQzNmFmYjE6MTIzNDU2";
		String url = "http://61.178.204.242:10001/auth/token";
		try {
			JSONObject json = ServiceInvocation.getResult(url, appId, appKey);
			System.out.println("getResult json: " + json.toString());
			
			String appid = json.getString("gateway_appid");
			String rtime = json.getString("gateway_rtime");
			String sign = json.getString("gateway_sig");
			String j = "{\n" 
					+ "   \"interfaceNo\":\"SX005\",\n" 
					+ "\t\"userType\": \"1\",\n"
					+ "\t\"deptCode\": \"\",\n" 
					+ "\t\"theme\": \"\",\n" 
					+ "\t\"searchKey\": \"\",\n"
					+ "\t\"pageNo\": \"1\",\n" 
					+ "\t\"isOnline\": \"1\",\n" 
					+ "\t\"pageSize\": \"10\"\n" 
					+ "}";
			
			OkHttpClient client = new OkHttpClient();
			MediaType mediaType = MediaType.parse("application/json");
			
			// System.out.println("request json: " + j);
			RequestBody body = RequestBody.create(mediaType, j);
			Request request = new Request.Builder()
					.url("http://61.178.204.242:10001/api/zlx/zwfwydyy")
					.post(body)
					.addHeader("gateway_appid", appid)
					.addHeader("gateway_rtime", rtime)
					.addHeader("gateway_sig", sign)
					.build();

			Response response = client.newCall(request).execute();
			System.out.println("Response json: " + response.body().string());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
