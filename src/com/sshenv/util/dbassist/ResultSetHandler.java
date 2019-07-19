package com.sshenv.util.dbassist;

import java.sql.ResultSet;

public interface ResultSetHandler {
	// 结果处理策略：抽象的
	Object handle(ResultSet rs);
}
