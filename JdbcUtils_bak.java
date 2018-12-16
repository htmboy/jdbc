package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author aooled-laptop
 * 单例模式
 */
public final class JdbcUtilsSing {
	private static String url = "jdbc:mysql://139.199.77.144:3306/jdbc";
	private static String user = "htmboy";
	private static String password = "htmboy8@MSN.com";
	
	//private static JdbcUtilsSing instance = new JdbcUtilsSing();
	private static JdbcUtilsSing instance = null;
	
	private JdbcUtilsSing() {	//不被创建对象之一, 另一种是加静态方法
	}
	
	public static JdbcUtilsSing getInstance() {
		if(instance == null)
			synchronized(JdbcUtilsSing.class) { // 加锁, 防止多线程同时获取对象
				if(instance == null) // 双重检查
					instance = new JdbcUtilsSing();
			}
		return instance;
	}
	
	static { // 类一加载就有
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}
	
	public static void free(ResultSet rs, Statement st, Connection conn) {
		try {
			if(rs != null)
				rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			try {
				if(st != null)
					st.close();
			}catch(SQLException e){
				e.printStackTrace();
			}finally {
				if(conn != null)
					try {
						conn.close();	
					}catch(SQLException e){
						e.printStackTrace();
					}
			}
		}
	}
}
