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
			
			// ��������
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection(); // ����ģʽ
			
			// �������
			st = conn.createStatement();
			
			// ִ�����
			rs = st.executeQuery("select * from user");
			
			// ������
			while(rs.next()) {
				System.out.println(rs.getObject(1) + "\t" + rs.getObject(2) + "\t" 
			+ rs.getObject(3) + "\t" + rs.getObject(4));
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
