package com.sshenv.gsww;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

public class ServiceInvocation {

	/**
	 * 密钥生成【核心网关】
	 * 
	 * @param appKey 授权码
	 * @param appId  请求者标识
	 * @param rtime  请求时间戳
	 * @return 加密后的sign，即head请求参数的gateway_sig
	 */
	public static String gatewaySignEncode(String appId, String appKey, String rtime) throws Exception {
		String inputString = appId + rtime;
		return encode(appKey, inputString);
	}

	private static String encode(String appKey, String inputStr) throws Exception {
		// 初始化加密生成器
		Mac hmacSha256 = Mac.getInstance("HmacSHA256");
		byte[] keyBytes = appKey.getBytes("UTF-8");
		System.out.println(new String(keyBytes, Charset.forName("UTF-8")));

		hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256"));

		byte[] hmacSha256Bytes = hmacSha256.doFinal(inputStr.getBytes("UTF-8"));
		System.out.println(new String(hmacSha256Bytes, Charset.forName("UTF-8")));

		String sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
		return sign;
	}

	/**
	 * 解密 解密过程： 1.构造密钥生成器 2.根据ecnodeRules规则初始化密钥生成器 3.产生密钥 4.创建和初始化密码器
	 * 5.将加密后的字符串反纺成byte[]数组 6.将加密内容解密
	 */
	public static String AESDecode(String encodeRules, String content) {
		String ivStr = "AESCBCPKCS5Paddi";
		try {
			// 构造秘钥生成器
			KeyGenerator keygen = KeyGenerator.getInstance("AES");

			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(encodeRules.getBytes());
			keygen.init(128, secureRandom);

			// 初始化密钥生成器
			SecretKey original_key = keygen.generateKey();

			// 产生密钥
			byte[] raw = original_key.getEncoded();
			SecretKey key = new SecretKeySpec(raw, "AES");

			// 初始化密码生成器
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(2, key, new IvParameterSpec(ivStr.getBytes("UTF-8")));

			// 加密后的字符串反纺成数组
			byte[] byte_content = new BASE64Decoder().decodeBuffer(content);

			// 加密内容解密
			byte[] byte_decode = cipher.doFinal(byte_content);
			String AES_decode = new String(byte_decode, "utf-8");
			return AES_decode;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 *
	 * @param appId  客户端ID
	 * @param appKey 应用公钥
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getResult(String generateTokenUrl, String appId, String appKey) throws Exception {
		String currTime = System.currentTimeMillis() + "";

		// String generateTokenUrl = "http://172.18.2.181:5000/proxy/auth/token";

		System.out.println("ServiceInvocation appid: " + appId);
		System.out.println("ServiceInvocation appKey: " + appKey);
		System.out.println("ServiceInvocation currTime: " + currTime);

		String sign = gatewaySignEncode(appId, appKey, currTime);

		System.out.println("ServiceInvocation sign: " + sign);
		
		System.out.println("ServiceInvocation generateTokenUrl: " + generateTokenUrl);

		JSONObject jsonObject = getRealToken(generateTokenUrl, appId, currTime, sign);
		JSONObject jsonObjectBody = JSONObject.parseObject(jsonObject.getString("body"));
		JSONObject serviceJsonData = new JSONObject();
		if (jsonObjectBody != null) {
			String accessToken = jsonObjectBody.getString("access_token");
			System.out.println("accessToken: " + accessToken + " , " + accessToken.length());

			String secretKey = AESDecode(appKey, accessToken);

			String sign1 = gatewaySignEncode(appId, secretKey, currTime);

			serviceJsonData.put("gateway_sig", sign1);
			serviceJsonData.put("gateway_rtime", currTime);
			serviceJsonData.put("gateway_appid", appId);
		} else {
			System.out.println("获取token错误");
		}
		return serviceJsonData;
	}

	/**
	 * 请求鉴权过程
	 * 
	 * @param generateTokenUrl 请求的url，在服务调用过程中url为获取token的url（格式为ip:port/auth/token）
	 * @param appId            获取token时该参数为appId；
	 * @param currTime         该参数为当前时间
	 * @param sign             该参数为head参数gateway_sig，由秘钥生成方法gatewaySignEncode生成。
	 * @return
	 */
	public static JSONObject getRealToken(String generateTokenUrl, String appId, String currTime, String sign)
			throws Exception {
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType, "");

		Request request = new Request.Builder().url(generateTokenUrl).post(body)
				.addHeader("Content-Type", "application/x-www-form-urlencoded").addHeader("gateway_sig", sign)
				.addHeader("gateway_rtime", currTime).addHeader("gateway_appid", appId)
				.addHeader("cache-control", "no-cache").build();
		Response response = client.newCall(request).execute();
		return JSONObject.parseObject(response.body().string());
	}
}