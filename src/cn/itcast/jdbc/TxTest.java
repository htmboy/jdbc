package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cn.itcast.jdbc.dao.DaoException;

public class TxTest {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		test();
		
	}
	
	static void test() throws SQLException{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false); // 关闭自动提交
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED); // 设置隔离级别 每个数据库都有不同的默认方式 一般使用默认的就可以
			// 隔离级别 -- 多线程并发读取数据时的读取方式
			// Connection.TRANSACTION_READ_COMMITTED 读已提交的事务
			// Connection.TRANSACTION_READ_UNCOMMITTED 读未提交的事务
			// Connection.TRANSACTION_REPEATABLE_READ 可重复读取
			// Connection.TRANSACTION_SERIALIZABLE 可串行化
			
			
			st = conn.createStatement();
			
			String sql = "update user set money = money + 200 where id = 1";
//			st.executeUpdate(sql);
			
			sql = "select money from user where id = 1";
			rs = st.executeQuery(sql);
			
			float money = 0.0f;
			if(rs.next()) {
				money = rs.getFloat("money");
			}
			if(money > 800)
				throw new RuntimeException("已经超过最大值!");
			sql = "update user set money = money + 10 where id = 2";
			st.executeUpdate(sql);
			conn.commit();
		} catch(SQLException e) {
			// 这里是不允许为空
			//e.printStackTrace();// 仅仅是打印, 程序还会继续运行
			if(conn != null)
				conn.rollback();
			throw new DaoException(e.getMessage(), e); // 不会污染接口
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
	}

}
