package cn.itcast.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cn.itcast.jdbc.JdbcUtils;
import cn.itcast.jdbc.dao.DaoException;
import cn.itcast.jdbc.dao.UserDao;
import cn.itcast.jdbc.domain.User;

public class UserDaoJdbcImpl implements UserDao {

	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = JdbcUtils.getConnection();
			String sql = "insert into user(name,birthday,money) values(?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getName());
			ps.setDate(2, new java.sql.Date(user.getBirthday().getTime()));
			ps.setFloat(3, user.getMoney());
			int i = ps.executeUpdate();
		} catch(SQLException e) {
			// 这里是不允许为空
			//e.printStackTrace();// 仅仅是打印, 程序还会继续运行
			throw new DaoException(e.getMessage(), e); // 不会污染接口
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}

	@Override
	public User getUser(int userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select id,name,birthday,money from user where id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while(rs.next()) {
				user = mappingUser(rs);
			}
		}catch(SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}finally {
			JdbcUtils.free(rs, ps, conn);
		}
		return user;
	}

	private User mappingUser(ResultSet rs) throws SQLException {
		User user;
		user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setBirthday(new java.sql.Date(rs.getDate("birthday").getTime()));
		user.setMoney(rs.getFloat("money"));
		return user;
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			// 建立连接
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection(); // 单例模式
			
			String sql = "update user set name=?,birthday=?,money=? where id=?";
			// 创建语句
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getName());
			ps.setDate(2, new java.sql.Date(user.getBirthday().getTime()));
			ps.setFloat(3, user.getMoney());
			ps.setInt(4, user.getId());
			
			int i = ps.executeUpdate();
			
			System.out.println("i = " + i);
		}catch(SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}

	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub
	
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			
			conn = JdbcUtils.getConnection();
			
			st = conn.createStatement();
			
			String sql = "delete from user where id=" + user.getId(); // 这里只做演示, 项目中要使用prepareStatement
			
			int i = st.executeUpdate(sql);
			
			System.out.println("i = " + i);
		}catch(SQLException e){
			throw new DaoException(e.getMessage(), e);
		}finally {
			JdbcUtils.free(rs, st, conn);
		}
	}
	
	public User findUser(String loginName, String password) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "select id,name,money,birthday from user where name=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginName);
			rs = ps.executeQuery();
			while(rs.next()) {
				user = mappingUser(rs);
			}
		}catch(SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}finally {
			JdbcUtils.free(rs, ps, conn);
		}
		return user;
	}
	
	
}
