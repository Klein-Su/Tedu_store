package cn.tedu.store;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataSourceTestCase {

	@Autowired
	DataSource dataSource;
	
	@Test //測試DataSuce連接，即測試連接資料庫的帳號和密碼
	public void getConnection() throws SQLException {
		System.out.println(dataSource.getConnection());
	}
}


