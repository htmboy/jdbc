package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author aooled-laptop
 * ��JDBCִ�й���
 */
public class CRUD {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		create();
//		update();
//		delete();
		read();
	}
	
	static void delete() throws Exception {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			
			// ��������
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection(); // ����ģʽ
			
			// �������
			st = conn.createStatement();
			
			String sql = "delete from user where id=4";
			
			// ִ�����
			// rs = st.executeQuery("select id,name,birthday,money from user"); // ��ѯ
			int i = st.executeUpdate(sql);
			
			System.out.println("i = " + i);
		}finally {
			JdbcUtils.free(rs, st, conn);
		}
		
	}
	
	static void update() throws Exception {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			
			// ��������
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection(); // ����ģʽ
			
			// �������
			st = conn.createStatement();
			
			String sql = "update user set money=money+10";
			
			// ִ�����
			// rs = st.executeQuery("select id,name,birthday,money from user"); // ��ѯ
			int i = st.executeUpdate(sql);
			
			System.out.println("i = " + i);
		}finally {
			JdbcUtils.free(rs, st, conn);
		}
		
	}
	
	static void create() throws Exception {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			
			// ��������
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection(); // ����ģʽ
			
			// �������
			st = conn.createStatement();
			
			String sql = "insert into user(name,birthday,money) values('name1','1987-01-01',400)";
			
			// ִ�����
			// rs = st.executeQuery("select id,name,birthday,money from user"); // ��ѯ
			int i = st.executeUpdate(sql);
			
			System.out.println("i = " + i);
		}finally {
			JdbcUtils.free(rs, st, conn);
		}
		
	}
	
	static void read() throws Exception {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			
			// ��������
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection(); // ����ģʽ
			
			// �������
			st = conn.createStatement();
			
			// ִ�����
			rs = st.executeQuery("select id,name,birthday,money from user");
			
			// ������
			while(rs.next()) {
				System.out.println(rs.getObject("id") + "\t" + rs.getObject("birthday") + "\t" 
			+ rs.getObject("name") + "\t" + rs.getObject("money"));
			}
		}finally {
			JdbcUtils.free(rs, st, conn);
		}
		
	}
	
	static void test() throws Exception {
		// 1.ע������
		// 1) ��һ�ַ�ʽ
		// ���ַ�ʽ�ᰤ��ȥѯ�ʲ�����, 
		// DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
		// 2) �ڶ��ַ�ʽ
		// ��ֵ��
		// System.setProperty("jdbc.drivers", "com.mysql.cj.jdbc.Driver");
		// System.setProperty("jdbc.drivers", "com.mysql.cj.jdbc.Driver:com.mysql.jdbc.Driver"); // ע����������:���������Ʒֿ�
		// 3) �����ַ�ʽ 
		// �Ƽ�, Class.forName�ǽ�����ص��������
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		// 2.��������
		String url = "jdbc:mysql://139.199.77.144:3306/jdbc";
		String user = "htmboy";
		String password = "htmboy8@MSN.com";
		Connection conn = DriverManager.getConnection(url, user, password);
		
		// 3.�������
		Statement st = conn.createStatement();
		
		// 4.ִ�����
		ResultSet rs = st.executeQuery("select * from user");
		
		// 5.������
		while(rs.next()) {
			System.out.println(rs.getObject(1) + "\t" + rs.getObject(2) + "\t" 
		+ rs.getObject(3) + "\t" + rs.getObject(4));
		}
		
		// 6.�ͷ���Դ
		rs.close();
		st.close();
		
		// conn ��������, �������ͷ�, һ����200�������Ǽ���
		conn.close();
	}

}
