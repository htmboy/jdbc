package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OtherApi {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
//		int id = create();
//		System.out.println("id:" + id);
		
		read();
	}
	
	static void read() throws Exception {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			
			// 建立连接
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection(); // 单例模式
			
			// 创建语句
			st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); // 获取的内容可以即时更新
			
			// 执行语句
			rs = st.executeQuery("select id,name,birthday,money from user where id < 5");
			
			// 处理结果
			while(rs.next()) {
				System.out.println(rs.getObject("id") + "\t" + rs.getObject("birthday") + "\t" 
			+ rs.getObject("name") + "\t" + rs.getObject("money"));
				String name = rs.getString("name");
//				if("lisi".equals(name)) { // 不推荐这种做法,不规范. 一般在更新里做
//					rs.updateFloat("money", 50f); // 对结果进行更新
//					rs.updateRow(); // 对结果执行更新
//				}
			}
			
		}finally {
			JdbcUtils.free(rs, st, conn);
		}
		
	}
	
	static int create() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		try {
			
			// 建立连接
			conn = JdbcUtils.getConnection();
			
			String sql = "insert into user(name,birthday,money) values('name2 gk','1987-01-01',400)";
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ps.executeUpdate();
			i = ps.executeUpdate();
			
		}finally {
			JdbcUtils.free(rs, ps, conn);
		}
		return i;
	}

}
