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
			
			// ��������
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection(); // ����ģʽ
			
			// �������
			st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); // ��ȡ�����ݿ��Լ�ʱ����
			
			// ִ�����
			rs = st.executeQuery("select id,name,birthday,money from user where id < 5");
			
			// ������
			while(rs.next()) {
				System.out.println(rs.getObject("id") + "\t" + rs.getObject("birthday") + "\t" 
			+ rs.getObject("name") + "\t" + rs.getObject("money"));
				String name = rs.getString("name");
//				if("lisi".equals(name)) { // ���Ƽ���������,���淶. һ���ڸ�������
//					rs.updateFloat("money", 50f); // �Խ�����и���
//					rs.updateRow(); // �Խ��ִ�и���
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
			
			// ��������
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
