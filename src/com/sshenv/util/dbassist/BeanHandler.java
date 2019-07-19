package com.sshenv.util.dbassist;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class BeanHandler implements ResultSetHandler {

	private Class clazz;

	public BeanHandler(Class clazz) {
		this.clazz = clazz;
	}

	public Object handle(ResultSet rs) {
		try {
			if (rs.next()) {
				// 知道结果中有几列和列名
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				Object bean = clazz.newInstance();
				for (int i = 0; i < columnCount; i++) {
					String feildName = rsmd.getColumnName(i + 1);// 也就拿到了JavaBean中的字段名
					Object fieldValue = rs.getObject(i + 1);// 字段值

					// 设置JavaBean中数据的字段值
					Field field = clazz.getDeclaredField(feildName);// 反射私有字段
					field.setAccessible(true);// 暴力反射
					field.set(bean, fieldValue);
				}
				return bean;
			} else {
				return null;// 没有结果
			}
		} catch (Exception e) {
			throw new RuntimeException("封装数据失败");
		}
	}

}
