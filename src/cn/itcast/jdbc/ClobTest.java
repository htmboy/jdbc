package cn.itcast.jdbc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ClobTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

//		create();
		read();
	}
	
	static void read() throws Exception {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			
			// 建立连接
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection(); // 单例模式
			
			// 创建语句
			st = conn.createStatement();
			
			// 执行语句
			rs = st.executeQuery("select big_text from clob_test");
			
			// 处理结果
			while(rs.next()) {
				Clob clob = rs.getClob(1);
				Reader reader = clob.getCharacterStream();
				reader = rs.getCharacterStream(1);
				File file = new File("JdbcUtils_bak.java");
				Writer writer = new BufferedWriter(new FileWriter(file));
				char[] buff = new char[1024];
				for(int i = 0; (i = reader.read(buff)) > 0;) {
					writer.write(buff, 0, i);
				}
				writer.close();
				reader.close();
			}
		}finally {
			JdbcUtils.free(rs, st, conn);
		}
		
	}

	static void create() throws Exception {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			// 建立连接
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection(); // 单例模式
			
			// 创建语句
			String sql = "insert into clob_test(big_text) values(?)";
			ps = conn.prepareStatement(sql);
			File file = new File("src/cn/itcast/jdbc/JdbcUtilsSing.java");
			Reader reader = new BufferedReader(new FileReader(file));
			ps.setCharacterStream(1, reader, file.length());
			
			// 执行语句
			
			int i = ps.executeUpdate();
			reader.close();
			System.out.println("i = " + i);
		}finally {
			JdbcUtils.free(rs, ps, conn);
		}
		
	}
}
