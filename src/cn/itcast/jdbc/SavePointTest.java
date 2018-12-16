package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import cn.itcast.jdbc.dao.DaoException;

public class SavePointTest {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		test();
		
	}
	
	static void test() throws SQLException{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Savepoint sp = null;
		try {
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false); // �ر��Զ��ύ
			st = conn.createStatement();
			String sql = "update user set money = money + 1 where id = 1";
			st.executeUpdate(sql);
			sp = conn.setSavepoint(); // ���ûع���
			
			sql = "select money from user where id = 2";
			rs = st.executeQuery(sql);
			
			float money = 0.0f;
			if(rs.next()) {
				money = rs.getFloat("money");
			}
			if(money > 200)
				throw new RuntimeException("�Ѿ��������ֵ!");
			sql = "update user set money = money + 10 where id = 2";
			st.executeUpdate(sql);
			conn.commit();
		} catch(RuntimeException e) {
			// �����ǲ�����Ϊ��
			//e.printStackTrace();// �����Ǵ�ӡ, ���򻹻��������
			if(conn != null && sp != null) {
				
				conn.rollback(sp); // ��ԭ�ع���
				conn.commit(); // ���ύ
			}
			throw new DaoException(e.getMessage(), e); // ������Ⱦ�ӿ�
		} catch(SQLException e){
			conn.rollback();
		}finally {
			JdbcUtils.free(rs, st, conn);
		}
	}

}
