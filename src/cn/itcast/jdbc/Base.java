package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author aooled-laptop
 * 简单JDBC执行过程
 */
public class Base {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// template();
		for(int i = 0; i < 13; i++) {
			Connection conn = JdbcUtils.getConnection();
			System.out.println(conn);
			JdbcUtils.free(null, null, conn);
		}
	}
	
	static void template() throws Exception {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			
			// 建立连接
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection(); // 单例模式
			
			// 创建语句
			st = conn.createStatement();
			
			// 执行语句
			rs = st.executeQuery("select * from user");
			
			// 处理结果
			while(rs.next()) {
				System.out.println(rs.getObject(1) + "\t" + rs.getObject(2) + "\t" 
			+ rs.getObject(3) + "\t" + rs.getObject(4));
			}
		}finally {
			JdbcUtils.free(rs, st, conn);
		}
		
	}
	
	static void test() throws Exception {
		// 1.注册驱动
		// 1) 第一种方式
		// 这种方式会挨个去询问并连接, 
		// DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
		// 2) 第二种方式
		// 键值对
		// System.setProperty("jdbc.drivers", "com.mysql.cj.jdbc.Driver");
		// System.setProperty("jdbc.drivers", "com.mysql.cj.jdbc.Driver:com.mysql.jdbc.Driver"); // 注册多个驱动用:把驱动名称分开
		// 3) 第三种方式 
		// 推荐, Class.forName是将类加载到虚拟机里
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		// 2.建立连接
		String url = "jdbc:mysql://139.199.77.144:3306/jdbc";
		String user = "htmboy";
		String password = "htmboy8@MSN.com";
		Connection conn = DriverManager.getConnection(url, user, password);
		
		// 3.创建语句
		Statement st = conn.createStatement();
		
		// 4.执行语句
		ResultSet rs = st.executeQuery("select * from user");
		
		// 5.处理结果
		while(rs.next()) {
			System.out.println(rs.getObject(1) + "\t" + rs.getObject(2) + "\t" 
		+ rs.getObject(3) + "\t" + rs.getObject(4));
		}
		
		// 6.释放资源
		rs.close();
		st.close();
		
		// conn 尽量晚创建, 尽量早释放, 一般在200个链接是极限
		conn.close();
	}

}
