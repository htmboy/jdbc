package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ScrollTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		read();
	}
	
	static void read() throws Exception {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			
			// 建立连接
			conn = JdbcUtils.getConnection();
			
			//st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);// 可滚动
			st = conn.createStatement();
			// 执行语句
			rs = st.executeQuery("select id,name,birthday,money from user where id < 10");
			
			// 处理结果
			while(rs.next()) {
				System.out.println(rs.getObject("id") + "\t" 
						+ rs.getObject("birthday") + "\t" 
						+ rs.getObject("name") + "\t" 
						+ rs.getObject("money"));
			}
			System.out.println("-------------------------");
			if(rs.previous()) {
				rs.absolute(4); // 指定到第几条
				rs.last(); // 最后一行
				System.out.println(rs.getObject("id") + "\t" 
						+ rs.getObject("birthday") + "\t" 
						+ rs.getObject("name") + "\t" 
						+ rs.getObject("money"));
			}
		}finally {
			JdbcUtils.free(rs, st, conn);
		}
		
	}

}
