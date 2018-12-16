package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultSetMetaDataTest {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> data = read("select * from user where id < 10");
		System.out.println(data);
	}
	
	static List<Map<String, Object>> read(String sql) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null; 
		ResultSet rs = null;
		Map<String, Object> data = null;
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		try {
			
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int count = rsmd.getColumnCount(); // �õ�����ж�����
			String[] colNames = new String[count];
			for(int i = 1; i <= count; i++) {
				// System.out.print(rsmd.getColumnClassName(i) + "\t"); // ����������ʲô
				// System.out.print(rsmd.getColumnName(i) + "\t"); // �����ֶ���
				// System.out.println(rsmd.getColumnLabel(i)); // �鿴�ֶα���
				colNames[i-1] = rsmd.getColumnName(i);
			}
			while(rs.next()) {
				data = new HashMap<String, Object>();
				for(int i = 0; i < colNames.length; i++) {
					data.put(colNames[i], rs.getObject(colNames[i]));
				}
				datas.add(data);
				
			}
			
		}finally {
			JdbcUtils.free(rs, ps, conn);
		}
		return datas;
	}

}
