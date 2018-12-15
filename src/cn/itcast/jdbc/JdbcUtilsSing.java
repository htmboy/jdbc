package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author aooled-laptop
 * ����ģʽ
 */
public final class JdbcUtilsSing {
	private static String url = "jdbc:mysql://139.199.77.144:3306/jdbc";
	private static String user = "htmboy";
	private static String password = "htmboy8@MSN.com";
	
	//private static JdbcUtilsSing instance = new JdbcUtilsSing();
	private static JdbcUtilsSing instance = null;
	
	private JdbcUtilsSing() {	//������������֮һ, ��һ���ǼӾ�̬����
	}
	
	public static JdbcUtilsSing getInstance() {
		if(instance == null)
			synchronized(JdbcUtilsSing.class) { // ����, ��ֹ���߳�ͬʱ��ȡ����
				if(instance == null) // ˫�ؼ��
					instance = new JdbcUtilsSing();
			}
		return instance;
	}
	
	static { // ��һ���ؾ���
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
