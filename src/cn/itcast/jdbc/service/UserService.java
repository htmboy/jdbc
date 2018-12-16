package cn.itcast.jdbc.service;

import java.sql.SQLException;

import cn.itcast.jdbc.dao.UserDao;

public class UserService {

	private UserDao userDao;
	
	public void regist(cn.itcast.jdbc.domain.User user) {
			this.userDao.addUser(user);
	}
}
