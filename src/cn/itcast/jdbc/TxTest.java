package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cn.itcast.jdbc.dao.DaoException;

public class TxTest {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		test();
		
	}
	
	static void test() throws SQLException{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false); // �ر��Զ��ύ
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED); // ���ø��뼶�� ÿ�����ݿⶼ�в�ͬ��Ĭ�Ϸ�ʽ һ��ʹ��Ĭ�ϵľͿ���
			// ���뼶�� -- ���̲߳�����ȡ����ʱ�Ķ�ȡ��ʽ
			// Connection.TRANSACTION_READ_COMMITTED �����ύ������
			// Connection.TRANSACTION_READ_UNCOMMITTED ��δ�ύ������
			// Connection.TRANSACTION_REPEATABLE_READ ���ظ���ȡ
			// Connection.TRANSACTION_SERIALIZABLE �ɴ��л�
			
			
			st = conn.createStatement();
			
			String sql = "update user set money = money + 200 where id = 1";
//			st.executeUpdate(sql);
			
			sql = "select money from user where id = 1";
			rs = st.executeQuery(sql);
			
			float money = 0.0f;
			if(rs.next()) {
				money = rs.getFloat("money");
			}
			if(money > 800)
				throw new RuntimeException("�Ѿ��������ֵ!");
			sql = "update user set money = money + 10 where id = 2";
			st.executeUpdate(sql);
			conn.commit();
		} catch(SQLException e) {
			// �����ǲ�����Ϊ��
			//e.printStackTrace();// �����Ǵ�ӡ, ���򻹻��������
			if(conn != null)
				conn.rollback();
			throw new DaoException(e.getMessage(), e); // ������Ⱦ�ӿ�
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
	}

}
