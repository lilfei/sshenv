package config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sshenv.util.dbassist.DBAssist;

/**
 * spring 配置类
 * 
 * @author Administrator
 *
 */
@Configuration
@ComponentScan("com.sshenv")
public class SpringConfiguration {

	/**
	 * 如果能找到唯一一个类型参数
	 * @param dataSource
	 * @return
	 */
	@Bean(name = "dbAssist")
	public DBAssist createDBAssist(DataSource dataSource) {
		return new DBAssist(dataSource);
	}

	@Bean(name = "dataSource")
	public DataSource createDataSource() {
		try {
			ComboPooledDataSource ds = new ComboPooledDataSource();
			ds.setDriverClass("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			ds.setJdbcUrl("jdbc:sqlserver://localhost:1433;databaseName=minij_test");
			ds.setUser("sa");
			ds.setPassword("3278");
			return ds;
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
