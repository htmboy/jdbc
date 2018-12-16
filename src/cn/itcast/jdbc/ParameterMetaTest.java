package cn.itcast.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParameterMetaTest {

	public static void main(String[] args) throws SQLException {
		Object[] params = new Object[] {"lisi"};
		
		// 需要写上generateSimpleParameterMetadata=true 就解决了。
		// 例如:jdbc.url=jdbc:mysql://192.168.0.221:3306/demo?useUnicode=true&generateSimpleParameterMetadata=true
		// read("select * from user where name = ? and birthday < ? and money > ?", params);
		read("select * from user where name = ?", params);
	}
	// 动态查询
	static void read(String sql, Object[] params) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null; 
		ResultSet rs = null;
		try {
			
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			ParameterMetaData pmd = ps.getParameterMetaData(); // 获取参数的元数据信息

			int count = pmd.getParameterCount(); // 获取sql 有几个?的参数
			for(int i = 1; i <= count; i++) {
//				System.out.print(pmd.getParameterClassName(i) + "\t"); // 获取参数类名
//				System.out.print(pmd.getParameterType(i) + "\t"); // 获取参数类型(数字常量)
//				System.out.println(pmd.getParameterTypeName(i)); // 获取参数类型名称
				ps.setObject(i, params[i-1]);
			}
			rs = ps.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getInt("id") + "\t" +rs.getString("name") + "\t" 
			+ rs.getDate("birthday") + "\t" + rs.getFloat("money"));
			}
			
		}finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}

}
