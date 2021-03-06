package cn.itcast.jdbc.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class MyDataSource {
	private static String url = "jdbc:mysql://139.199.77.144:3306/jdbc";
	private static String user = "htmboy";
	private static String password = "htmboy8@MSN.com";
	
	private static int initCount = 5;
	private static int maxCount = 10;
	private int currentCount = 0;
	// LinkedList 对增删效率高
	// ArrayList 对查询效率高
	LinkedList<Connection> connectionPool = new LinkedList<Connection>();
	
	public MyDataSource() {
		try {
			
			for(int i = 0; i < initCount; i++) {
				this.connectionPool.addLast(this.createConnection());
				this.currentCount++;
			}
		}catch(SQLException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private Connection createConnection() throws SQLException {
		// TODO Auto-generated method stub
		return DriverManager.getConnection(url, user, password);
	}
	
	public Connection getConnection() throws SQLException {
		synchronized (connectionPool) {
			if(this.connectionPool.size() > 0)
				return this.connectionPool.removeFirst();
			if(this.currentCount < maxCount) {
				this.currentCount++;
				return this.createConnection();
			}
			throw new SQLException("已没有连接了");
		}
	}
	
	public void free(Connection conn) {
		this.connectionPool.addLast(conn);
	}
}