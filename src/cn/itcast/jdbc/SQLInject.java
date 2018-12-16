package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLInject {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		read("zhangsan"); // sqlע�빥��
		
		
		
		
//		read1("zhangsan");
		
	}
	
static void read(String name) throws Exception { //���ٶȿ���Ԥ����, û�в���ʱ����ʹ�ô˷���
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			
			// ��������
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection(); // ����ģʽ
			long start = System.currentTimeMillis();
			// �������
			st = conn.createStatement();
			
			// ִ�����
			rs = st.executeQuery("select id,name,birthday,money from user where name='" + name + "'");
			
			// ������
			while(rs.next()) {
				System.out.println(rs.getInt("id") + "\t" + rs.getDate("birthday") // ֻ������, û��ʱ��
				+ "\t" 
			+ rs.getString("name") + "\t" + rs.getFloat("money"));
			}
			long end = System.currentTimeMillis();
			System.out.println("read:" + (end - start));
		}finally {
			JdbcUtils.free(rs, st, conn);
		}
		
	}
	
	static void read1(String name) throws Exception { // ����read, ��������, ʹ��Ԥ����
		
		Connection conn = null;
		PreparedStatement ps = null; // Ԥ���� ������������
		ResultSet rs = null;
		try {
			
			// ��������
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection(); // ����ģʽ
			long start = System.currentTimeMillis();
			// �������
			String sql = "select id,name,birthday,money from user where name=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			// ִ�����
			rs = ps.executeQuery();
//			System.out.println(sql);
			// ������
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
