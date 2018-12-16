package cn.itcast.jdbc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class BlobTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
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
			rs = st.executeQuery("select big_bit from blob_text");
			
			// 处理结果
			while(rs.next()) {
//				InputStream in = rs.getBlob(1);
				InputStream in = rs.getBinaryStream(1);
				
				
				File file = new File("point2.png");
				OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
				byte[] buff = new byte[1024];
				for(int i = 0; (i = in.read(buff)) > 0;) {
					out.write(buff, 0, i);
				}
				out.close();
				in.close();
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
			String sql = "insert into blob_text(big_bit) values(?)"; // blob 最大支持64k
			ps = conn.prepareStatement(sql);
			File file = new File("point.png");
			InputStream in = new BufferedInputStream(new FileInputStream(file));
			ps.setBinaryStream(1, in, file.length());
			
			// 执行语句
			
			int i = ps.executeUpdate();
			in.close();
			System.out.println("i = " + i);
		}finally {
			JdbcUtils.free(rs, ps, conn);
		}
		
	}
}
