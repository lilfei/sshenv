package com.sshenv.util.dbassist;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class DBAssist {
	private DataSource dataSource;

	public DBAssist(DataSource dataSource) {// 由调用者传入数据源
		this.dataSource = dataSource;
	}

	/**
	 * 执行DML语句：insert update delete
	 * 
	 * @param sql    执行的sql语句
	 * @param params 语句中占位符对应的参数
	 */
	public void update(String sql, Object[] params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);

			// 得到sql语句中占位符的个数
			ParameterMetaData pmd = stmt.getParameterMetaData();
			int paramCount = pmd.getParameterCount();
			if (paramCount > 0) {
				// 有占位符
				if (params == null || params.length == 0) {
					throw new RuntimeException("参数有误");
				}
				// 对比占位符的数量和参数的个数是否匹配
				if (paramCount != params.length) {
					throw new RuntimeException("参数个数不匹配");
				}
				// 设置对应的参数
				for (int i = 0; i < paramCount; i++) {
					stmt.setObject(i + 1, params[i]);
				}
			}

			stmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			release(rs, stmt, conn);
		}
	}

	/**
	 * 前提：JavaBean中的字段名和数据库字段名必须一致
	 * 
	 * @param sql
	 * @param params
	 * @param handler
	 * @return
	 */
	public Object query(String sql, Object params[], ResultSetHandler handler) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);

			// 得到sql语句中占位符的个数
			ParameterMetaData pmd = stmt.getParameterMetaData();
			int paramCount = pmd.getParameterCount();
			if (paramCount > 0) {
				// 有占位符
				if (params == null || params.length == 0) {
					throw new RuntimeException("参数有误");
				}
				// 对比占位符的数量和参数的个数是否匹配
				if (paramCount != params.length) {
					throw new RuntimeException("参数个数不匹配");
				}
				// 设置对应的参数
				for (int i = 0; i < paramCount; i++) {
					stmt.setObject(i + 1, params[i]);
				}
			}

			rs = stmt.executeQuery();
			// 关键任务：把结果集中的记录封装到JavaBean中。谁用谁知道，框架不知道（策略设计模式）
			return handler.handle(rs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			release(rs, stmt, conn);
		}
	}

	private static void release(ResultSet rs, Statement stmt, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stmt = null;
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}
}
