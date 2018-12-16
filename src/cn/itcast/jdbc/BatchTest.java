package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchTest {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
//		for(int i = 0; i < 1000; i++) //批处理数据库
//			create(i);
		createBatch();
	}
	
	static void createBatch() throws SQLException { // 批处理数据库
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			
			String sql = "insert into user(name, birthday, money) values(?,?,?)";
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			for(int i = 0; i < 1000; i++) {
				ps.setString(1, " no batch name" + i);
				ps.setDate(2, new Date(System.currentTimeMillis()));
				ps.setFloat(3, 100f + i);
				ps.addBatch();
			}
			
			
			int[] is = ps.executeBatch();
			
			System.out.println(is.toString());
		}finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}
	
	static int create(int i) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			
			String sql = "insert into user(name, birthday, money) values(?,?,?)";
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, "batch name" + i);
			ps.setDate(2, new Date(System.currentTimeMillis()));
			ps.setFloat(3, 100f + i);
			
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			int id = 0;
			if(rs.next())
				id = rs.getInt(1);
			return id;
		}finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}
}
