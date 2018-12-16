package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class DBMD {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Connection conn = JdbcUtils.getConnection();
		DatabaseMetaData dbmd = conn.getMetaData(); // �������ݿ��ԭ��Ϣ
		System.out.println(dbmd.getDatabaseProductName()); // ��ȡ���ݿ������
		System.out.println(dbmd.supportsTransactions()); // �Ƿ�֧������
		conn.close();
	}

}
