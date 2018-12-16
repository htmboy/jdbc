package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLInject {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		read("zhangsan"); // sql注入攻击
		
		
		
		
//		read1("zhangsan");
		
	}
	
static void read(String name) throws Exception { //此速度快于预处理, 没有参数时可以使用此方法
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			
			// 建立连接
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection(); // 单例模式
			long start = System.currentTimeMillis();
			// 创建语句
			st = conn.createStatement();
			
			// 执行语句
			rs = st.executeQuery("select id,name,birthday,money from user where name='" + name + "'");
			
			// 处理结果
			while(rs.next()) {
				System.out.println(rs.getInt("id") + "\t" + rs.getDate("birthday") // 只有日期, 没有时间
				+ "\t" 
			+ rs.getString("name") + "\t" + rs.getFloat("money"));
			}
			long end = System.currentTimeMillis();
			System.out.println("read:" + (end - start));
		}finally {
			JdbcUtils.free(rs, st, conn);
		}
		
	}
	
	static void read1(String name) throws Exception { // 慢于read, 有条件的, 使用预处理
		
		Connection conn = null;
		PreparedStatement ps = null; // 预处理 交给驱动处理
		ResultSet rs = null;
		try {
			
			// 建立连接
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection(); // 单例模式
			long start = System.currentTimeMillis();
			// 创建语句
			String sql = "select id,name,birthday,money from user where name=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			// 执行语句
			rs = ps.executeQuery();
//			System.out.println(sql);
			// 处理结果
			while(rs.next()) {
				System.out.println(rs.getObject("id") + "\t" + rs.getObject("birthday") + "\t" 
			+ rs.getObject("name") + "\t" + rs.getObject("money"));
			}
			long end = System.currentTimeMillis();
			System.out.println("read1:" + (end - start));
		}finally {
			JdbcUtils.free(rs, ps, conn);
		}
		
	}
}
