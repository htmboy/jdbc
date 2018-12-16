package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class DBMD {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Connection conn = JdbcUtils.getConnection();
		DatabaseMetaData dbmd = conn.getMetaData(); // 返回数据库的原信息
		System.out.println(dbmd.getDatabaseProductName()); // 获取数据库的名字
		System.out.println(dbmd.supportsTransactions()); // 是否支持事务
		conn.close();
	}

}
