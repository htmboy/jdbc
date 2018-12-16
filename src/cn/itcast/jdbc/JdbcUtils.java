package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cn.itcast.jdbc.datasource.MyDataSource;

/**
 * @author aooled-laptop
 * �Ż�base��
 */
public final class JdbcUtils {
	private static String url = "jdbc:mysql://139.199.77.144:3306/jdbc?generateSimpleParameterMetadata=true";
	private static String user = "htmboy";
	private static String password = "htmboy8@MSN.com";
	private static MyDataSource myDataSource = null;
	private JdbcUtils() {	//������������֮һ, ��һ���ǼӾ�̬����
	}
	
	static { // ��һ���ؾ���
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			myDataSource = new MyDataSource();
		}catch(ClassNotFoundException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static Connection getConnection() throws SQLException {
		// retrn DriverManager.getConnection(url, user, password);
		return myDataSource.getConnection();
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
						// conn.close();
						myDataSource.free(conn);
					}catch(Exception e){
						e.printStackTrace();
					}
			}
		}
	}
}
