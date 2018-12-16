package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import cn.itcast.jdbc.dao.DaoException;

public class SavePointTest {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		test();
		
	}
	
	static void test() throws SQLException{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Savepoint sp = null;
		try {
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false); // 关闭自动提交
			st = conn.createStatement();
			String sql = "update user set money = money + 1 where id = 1";
			st.executeUpdate(sql);
			sp = conn.setSavepoint(); // 设置回滚点
			
			sql = "select money from user where id = 2";
			rs = st.executeQuery(sql);
			
			float money = 0.0f;
			if(rs.next()) {
				money = rs.getFloat("money");
			}
			if(money > 200)
				throw new RuntimeException("已经超过最大值!");
			sql = "update user set money = money + 10 where id = 2";
			st.executeUpdate(sql);
			conn.commit();
		} catch(RuntimeException e) {
			// 这里是不允许为空
			//e.printStackTrace();// 仅仅是打印, 程序还会继续运行
			if(conn != null && sp != null) {
				
				conn.rollback(sp); // 还原回滚点
				conn.commit(); // 并提交
			}
			throw new DaoException(e.getMessage(), e); // 不会污染接口
		} catch(SQLException e){
			conn.rollback();
		}finally {
			JdbcUtils.free(rs, st, conn);
		}
	}

}
