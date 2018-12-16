package cn.itcast.jdbc;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.itcast.jdbc.domain.User;

public class ORMTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		User user = (User) getObject("select id as Id, name as Name, birthday as Birthday, money as Money from user where id = 1", 
				User.class);
		System.out.println(user);
		
		Bean b = (Bean) getObject("select id as Id, name as Name, birthday as Birthday, money as Money from user where id = 1", Bean.class);
		System.out.println(b);
	}
	
	static Object getObject(String sql, Class clazz) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null; 
		ResultSet rs = null;
		Object obj = null;
		try {
			
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int count = rsmd.getColumnCount(); // 拿到结果有多少列
			String[] colNames = new String[count];
			for(int i = 1; i <= count; i++) {
				colNames[i-1] = rsmd.getColumnLabel(i);
			}
			if(rs.next()) {
				obj = clazz.getConstructor().newInstance();
				
				for(int i = 0; i < colNames.length; i++) {
					String colName = colNames[i];
					
					String methodName = "set" + colName;
					Method[] ms = obj.getClass().getMethods();
					
					for(Method m : ms) {
						
						if(methodName.equals(m.getName())) {
							
							if(rs.getObject(colName) instanceof Long) {
								m.invoke(obj, Integer.parseInt(rs.getObject(colName).toString()));
								
							}else {
								
								m.invoke(obj, rs.getObject(colName));
							}
						}
					}
						
				}
				
			}
			
		}finally {
			JdbcUtils.free(rs, ps, conn);
		}
		return obj;
	}

}
